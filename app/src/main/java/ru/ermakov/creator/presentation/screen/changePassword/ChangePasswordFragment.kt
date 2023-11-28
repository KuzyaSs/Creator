package ru.ermakov.creator.presentation.screen.changePassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentChangePasswordBinding
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class ChangePasswordFragment : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var changePasswordViewModelFactory: ChangePasswordViewModelFactory
    private lateinit var changePasswordViewModel: ChangePasswordViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        changePasswordViewModel = ViewModelProvider(
            this,
            changePasswordViewModelFactory
        )[ChangePasswordViewModel::class.java]
        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.apply {
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonConfirm.setOnClickListener { confirmNewPassword() }
        }
    }

    private fun setUpObservers() {
        changePasswordViewModel.changePasswordUiState.observe(viewLifecycleOwner) { changePasswordUiState ->
            changePasswordUiState.apply {
                setLoading(isLoadingShown = isProgressBarConfirmShown)
                setErrorMessage(
                    errorMessage = changePasswordErrorMessage,
                    isErrorMessageShown = isChangePasswordErrorMessageShown
                )
            }
        }
    }

    private fun confirmNewPassword() {
        val currentPassword = binding.textInputEditTextCurrentPassword.text.toString()
        val newPassword = binding.textInputEditTextNewPassword.text.toString()
        val confirmationNewPassword = binding.textInputEditTextConfirmNewPassword.text.toString()
        changePasswordViewModel.confirmNewPassword(
            currentPassword = currentPassword,
            newPassword = newPassword,
            confirmationNewPassword = confirmationNewPassword
        )
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setLoading(isLoadingShown: Boolean) {
        binding.apply {
            progressBarConfirm.isVisible = isLoadingShown
            buttonConfirm.visibility = if (isLoadingShown) View.INVISIBLE else View.VISIBLE
        }
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        binding.textViewErrorMessage.apply {
            text = textLocalizer.localizeText(text = errorMessage)
            isVisible = isErrorMessageShown
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}