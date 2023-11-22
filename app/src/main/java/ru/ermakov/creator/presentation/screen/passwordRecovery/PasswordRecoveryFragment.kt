package ru.ermakov.creator.presentation.screen.passwordRecovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentPasswordRecoveryBinding
import ru.ermakov.creator.presentation.model.State
import ru.ermakov.creator.presentation.exception.ExceptionLocalizer
import javax.inject.Inject

class PasswordRecoveryFragment : Fragment() {
    private val arguments: PasswordRecoveryFragmentArgs by navArgs()

    private var _binding: FragmentPasswordRecoveryBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var passwordRecoveryViewModelFactory: PasswordRecoveryViewModelFactory
    private lateinit var passwordRecoveryViewModel: PasswordRecoveryViewModel

    @Inject
    lateinit var exceptionLocalizer: ExceptionLocalizer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        passwordRecoveryViewModel = ViewModelProvider(
            this,
            passwordRecoveryViewModelFactory
        )[PasswordRecoveryViewModel::class.java]
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
        passwordRecoveryViewModel.passwordRecoveryUiState.observe(viewLifecycleOwner) { passwordRecoveryUiState ->
            when (passwordRecoveryUiState.state) {
                State.INITIAL -> {}
                State.SUCCESS -> {
                    hideProgressBar()
                    setEmailSendingSuccessfulState()
                    showMessage(getString(R.string.password_recovery_email))
                }

                State.ERROR -> {
                    hideProgressBar()
                    val errorMessage = exceptionLocalizer.localizeException(
                        errorMessage = passwordRecoveryUiState.errorMessage
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

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
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
            buttonResetPassword.visibility = View.INVISIBLE
        }
    }

    private fun hideProgressBar() {
        binding.apply {
            progressBar.isVisible = false
            buttonResetPassword.isVisible = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}