package ru.ermakov.creator.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentSignUpBinding
import ru.ermakov.creator.domain.model.SignUpData
import ru.ermakov.creator.presentation.viewModel.signUp.SignUpViewModel
import ru.ermakov.creator.presentation.viewModel.signUp.SignUpViewModelFactory
import ru.ermakov.creator.util.Constant.Companion.EMAIL_COLLISION_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.EMAIL_FORMAT_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.EMPTY_DATA_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.EMPTY_STRING
import ru.ermakov.creator.util.Constant.Companion.INVALID_USER_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.NETWORK_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.PASSWORDS_DON_T_MATCH_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.WEAK_PASSWORD_EXCEPTION
import ru.ermakov.creator.util.Resource
import javax.inject.Inject

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val signUpViewModel: SignUpViewModel by viewModels {
        signUpViewModelFactory
    }

    @Inject
    lateinit var signUpViewModelFactory: SignUpViewModelFactory

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
        signUpViewModel.user.observe(viewLifecycleOwner) { userResource ->
            when (userResource) {
                is Resource.Success -> {
                    hideProgressBar()
                    if (signUpViewModel.isNavigationAvailable) {
                        navigateToSignUpFragment(userResource.data?.email ?: EMPTY_STRING)
                        signUpViewModel.resetIsNavigationAvailable()
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    val errorMessage = defineException(
                        exceptionString = userResource.message ?: EMPTY_STRING
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

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
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

    private fun navigateToSignUpFragment(email: String) {
        val action = SignUpFragmentDirections
            .actionSignUpFragmentToVerificationEmailFragment(email = email)
        findNavController().navigate(action)
    }

    private fun defineException(exceptionString: String): String {
        return when (exceptionString) {
            NETWORK_EXCEPTION -> {
                getString(R.string.network_exception)
            }

            EMPTY_DATA_EXCEPTION -> {
                getString(R.string.empty_data_exception)
            }

            EMAIL_FORMAT_EXCEPTION -> {
                getString(R.string.email_format_exception)
            }

            PASSWORDS_DON_T_MATCH_EXCEPTION -> {
                getString(R.string.passwords_don_t_match_exception)
            }

            WEAK_PASSWORD_EXCEPTION -> {
                getString(R.string.weak_password_exception)

            }

            EMAIL_COLLISION_EXCEPTION -> {
                getString(R.string.email_collision_exception)
            }

            INVALID_USER_EXCEPTION -> {
                getString(R.string.invalid_user_exception)
            }

            else -> {
                getString(R.string.unknown_exception)
            }
        }
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

    private fun showError(errorMessage: String) {
        binding.textViewError.apply {
            text = errorMessage
            isVisible = true
        }
    }

    private fun hideError() {
        binding.textViewError.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}