package ru.ermakov.creator.presentation.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentSignInBinding
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.presentation.viewModel.auth.signIn.SignInViewModel
import ru.ermakov.creator.presentation.viewModel.auth.signIn.SignInViewModelFactory
import ru.ermakov.creator.util.Constant.Companion.EMAIL_FORMAT_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.EMAIL_VERIFICATION_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.EMPTY_DATA_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.EMPTY_STRING
import ru.ermakov.creator.util.Constant.Companion.INVALID_PASSWORD_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.INVALID_USER_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.NETWORK_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.TOO_MANY_REQUESTS_EXCEPTION
import ru.ermakov.creator.util.Resource
import javax.inject.Inject

class SignInFragment : Fragment() {
    private val arguments: SignInFragmentArgs by navArgs()
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val signInViewModel: SignInViewModel by viewModels {
        signInViewModelFactory
    }

    @Inject
    lateinit var signInViewModelFactory: SignInViewModelFactory

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
        setUpListeners()
        setUpObservers()
        setEmail()
    }

    private fun setEmail() {
        binding.textInputEditTextEmail.setText(arguments.email)
    }

    private fun setUpListeners() {
        binding.buttonSignIn.setOnClickListener { signIn() }
        binding.textViewForgotPassword.setOnClickListener { navigateToPasswordRecoveryFragment() }
        binding.textViewSignUp.setOnClickListener { navigateToSignUpFragment() }
    }

    private fun setUpObservers() {
        signInViewModel.signInData.observe(viewLifecycleOwner) { signInDataResource ->
            when (signInDataResource) {
                is Resource.Success -> {
                    hideProgressBar()
                    navigateToFeedFragment()
                }

                is Resource.Error -> {
                    hideProgressBar()
                    val errorMessage = localizeError(
                        errorMessage = signInDataResource.message ?: EMPTY_STRING
                    )
                    showError(errorMessage = errorMessage)
                }

                is Resource.Loading -> {
                    hideError()
                    showProgressBar()
                }
            }
        }
    }

    private fun signIn() {
        binding.apply {
            val signInData = SignInData(
                email = textInputEditTextEmail.text.toString(),
                password = textInputEditTextPassword.text.toString()
            )
            signInViewModel.signIn(signInData = signInData)
        }
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

    private fun localizeError(errorMessage: String): String {
        return when (errorMessage) {
            NETWORK_EXCEPTION -> {
                getString(R.string.network_exception)
            }

            EMPTY_DATA_EXCEPTION -> {
                getString(R.string.empty_data_exception)
            }

            EMAIL_FORMAT_EXCEPTION -> {
                getString(R.string.email_format_exception)
            }

            EMAIL_VERIFICATION_EXCEPTION -> {
                getString(R.string.email_verification_exception)
            }

            INVALID_PASSWORD_EXCEPTION -> {
                getString(R.string.invalid_password_exception)
            }

            INVALID_USER_EXCEPTION -> {
                getString(R.string.invalid_user_exception)
            }

            TOO_MANY_REQUESTS_EXCEPTION -> {
                getString(R.string.too_many_requests_exception)
            }

            else -> {
                getString(R.string.unknown_exception)
            }
        }
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

    private fun showError(errorMessage: String) {
        binding.textViewErrorMessage.apply {
            text = errorMessage
            isVisible = true
        }
    }

    private fun hideError() {
        binding.textViewErrorMessage.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}