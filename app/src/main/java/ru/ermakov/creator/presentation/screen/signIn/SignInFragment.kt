package ru.ermakov.creator.presentation.screen.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentSignInBinding
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.presentation.State
import ru.ermakov.creator.presentation.exception.ExceptionLocalizer
import javax.inject.Inject

class SignInFragment : Fragment() {
    private val arguments: SignInFragmentArgs by navArgs()

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var signInViewModelFactory: SignInViewModelFactory
    private lateinit var signInViewModel: SignInViewModel

    @Inject
    lateinit var exceptionLocalizer: ExceptionLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        signInViewModel =
            ViewModelProvider(this, signInViewModelFactory)[SignInViewModel::class.java]
        setUpListeners()
        setUpObservers()
        setEmail()
    }

    private fun setUpListeners() {
        binding.apply {
            buttonSignIn.setOnClickListener { signIn() }
            textViewForgotPassword.setOnClickListener { navigateToPasswordRecoveryFragment() }
            textViewSignUp.setOnClickListener { navigateToSignUpFragment() }
        }
    }

    private fun setUpObservers() {
        signInViewModel.signInUiState.observe(viewLifecycleOwner) { signInUiState ->
            when (signInUiState.state) {
                State.INITIAL -> {}
                State.SUCCESS -> {
                    hideProgressBar()
                    navigateToFeedFragment()
                }

                State.ERROR -> {
                    hideProgressBar()
                    val errorMessage = exceptionLocalizer.localizeException(
                        errorMessage = signInUiState.errorMessage
                    )
                    showError(errorMessage = errorMessage)
                }

                State.LOADING -> {
                    hideError()
                    showProgressBar()
                }
            }
        }
    }

    private fun setEmail() {
        binding.textInputEditTextEmail.setText(arguments.email)
    }

    private fun signIn() {
        val signInData = SignInData(
            email = binding.textInputEditTextEmail.text.toString(),
            password = binding.textInputEditTextPassword.text.toString()
        )
        signInViewModel.signIn(signInData = signInData)
    }

    private fun navigateToPasswordRecoveryFragment() {
        val action = SignInFragmentDirections.actionSignInFragmentToPasswordRecoveryFragment(
            binding.textInputEditTextEmail.text.toString()
        )
        findNavController().navigate(action)
    }

    private fun navigateToSignUpFragment() {
        val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        findNavController().navigate(action)
    }

    private fun navigateToFeedFragment() {
        val action = SignInFragmentDirections.actionSignInFragmentToFeedFragment()
        findNavController().navigate(action)
    }

    private fun showError(errorMessage: String) {
        binding.textViewErrorMessage.apply {
            text = errorMessage
            isVisible = true
        }
    }

    private fun hideError() {
        binding.textViewErrorMessage.isVisible = false
    }

    private fun showProgressBar() {
        binding.apply {
            progressBar.isVisible = true
            buttonSignIn.visibility = View.INVISIBLE
        }
    }

    private fun hideProgressBar() {
        binding.apply {
            progressBar.isVisible = false
            buttonSignIn.isVisible = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}