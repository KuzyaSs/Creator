package ru.ermakov.creator.presentation.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentPasswordRecoveryBinding
import ru.ermakov.creator.presentation.viewModel.auth.passwordRecovery.PasswordRecoveryViewModel
import ru.ermakov.creator.presentation.viewModel.auth.passwordRecovery.PasswordRecoveryViewModelFactory
import ru.ermakov.creator.util.Constant.Companion.EMAIL_FORMAT_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.EMPTY_DATA_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.EMPTY_STRING
import ru.ermakov.creator.util.Constant.Companion.INVALID_USER_EXCEPTION
import ru.ermakov.creator.util.Constant.Companion.NETWORK_EXCEPTION
import ru.ermakov.creator.util.Resource
import javax.inject.Inject

class PasswordRecoveryFragment : Fragment() {
    private val arguments: PasswordRecoveryFragmentArgs by navArgs()

    private var _binding: FragmentPasswordRecoveryBinding? = null
    private val binding get() = _binding!!

    private val passwordRecoveryViewModel: PasswordRecoveryViewModel by viewModels {
        passwordRecoveryViewModelFactory
    }

    @Inject
    lateinit var passwordRecoveryViewModelFactory: PasswordRecoveryViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        setUpListeners()
        setUpObservers()
        setEmail()
    }

    private fun setUpListeners() {
        binding.apply {
            imageViewBack.setOnClickListener { goBack() }
            buttonResetPassword.setOnClickListener { resetPassword() }
        }
    }

    private fun setUpObservers() {
        passwordRecoveryViewModel.email.observe(viewLifecycleOwner) { emailResource ->
            when (emailResource) {
                is Resource.Success -> {
                    hideProgressBar()
                    setEmailSendingSuccessfulState()
                    showMessage(getString(R.string.password_recovery_email))
                }

                is Resource.Error -> {
                    hideProgressBar()
                    val errorMessage = localizeError(
                        errorMessage = emailResource.message ?: EMPTY_STRING
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

    private fun setEmail() {
        binding.textInputEditTextEmail.setText(arguments.email)
    }

    private fun setEmailSendingSuccessfulState() {
        binding.apply {
            buttonResetPassword.text = getString(R.string._continue)
            buttonResetPassword.setOnClickListener { goBack() }
            textInputEditTextEmail.isEnabled = false
        }
    }

    private fun resetPassword() {
        passwordRecoveryViewModel.resetPassword(binding.textInputEditTextEmail.text.toString())
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
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

            INVALID_USER_EXCEPTION -> {
                getString(R.string.invalid_user_exception)
            }

            else -> {
                getString(R.string.unknown_exception)
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    private fun showProgressBar() {
        binding.apply {
            progressBar.isVisible = true
            buttonResetPassword.visibility = View.INVISIBLE
        }
    }

    private fun hideProgressBar() {
        binding.apply {
            progressBar.isVisible = false
            buttonResetPassword.isVisible = true
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