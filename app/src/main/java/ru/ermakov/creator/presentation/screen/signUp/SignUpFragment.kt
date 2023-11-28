package ru.ermakov.creator.presentation.screen.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentSignUpBinding
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.presentation.util.State
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var signUpViewModelFactory: SignUpViewModelFactory
    private lateinit var signUpViewModel: SignUpViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        signUpViewModel =
            ViewModelProvider(this, signUpViewModelFactory)[SignUpViewModel::class.java]
        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.apply {
            imageViewBack.setOnClickListener { goBack() }
            buttonSignUp.setOnClickListener { signUp() }
        }
    }

    private fun setUpObservers() {
        signUpViewModel.signUpUiState.observe(viewLifecycleOwner) { signUpUiState ->
            when (signUpUiState.state) {
                State.INITIAL -> {}
                State.SUCCESS -> {
                    navigateToVerificationEmailFragment(signUpUiState.signUpData!!.email)
                    signUpViewModel.setInitialStateWithSignUpData()
                }

                State.ERROR -> {
                    hideProgressBar()
                    val errorMessage = textLocalizer.localizeText(
                        text = signUpUiState.errorMessage
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

    private fun signUp() {
        binding.apply {
            val signUpData = SignUpData(
                email = textInputEditTextEmail.text.toString(),
                password = textInputEditTextPassword.text.toString(),
                confirmationPassword = textInputEditTextConfirmPassword.text.toString()
            )
            signUpViewModel.signUp(signUpData)
        }
    }

    private fun navigateToVerificationEmailFragment(email: String?) {
        val action =
            SignUpFragmentDirections.actionSignUpFragmentToVerificationEmailFragment(email = email.toString())
        findNavController().navigate(action)
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
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
            buttonSignUp.isVisible = false
        }
    }

    private fun hideProgressBar() {
        binding.apply {
            progressBar.isVisible = false
            buttonSignUp.isVisible = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}