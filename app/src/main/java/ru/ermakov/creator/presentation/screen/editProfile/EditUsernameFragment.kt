package ru.ermakov.creator.presentation.screen.editProfile

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentEditUsernameBinding
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class EditUsernameFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentEditUsernameBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var editProfileViewModelFactory: EditProfileViewModelFactory
    private lateinit var editProfileViewModel: EditProfileViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditUsernameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        editProfileViewModel =
            ViewModelProvider(
                requireParentFragment(),
                editProfileViewModelFactory
            )[EditProfileViewModel::class.java]
        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.apply {
            imageViewCheck.setOnClickListener { editUsername() }
        }
    }

    private fun setUpObservers() {
        editProfileViewModel.editProfileUiState.observe(viewLifecycleOwner) { editProfileUiState ->
            editProfileUiState.apply {
                if (binding.textInputEditTextUsername.text.isNullOrEmpty()) {
                    binding.textInputEditTextUsername.setText(editProfileUiState.currentUser?.username)
                }
                setErrorMessage(
                    errorMessage = editUsernameErrorMessage,
                    isErrorMessageShown = isEditUsernameErrorMessageShown
                )
            }
        }
    }

    private fun editUsername() {
        val username = binding.textInputEditTextUsername.text?.trim().toString()
        editProfileViewModel.editUsername(username = username)
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        binding.textViewErrorMessage.apply {
            text = textLocalizer.localizeText(text = errorMessage)
            isVisible = isErrorMessageShown
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        binding.textInputEditTextUsername.setText(
            editProfileViewModel.editProfileUiState.value?.currentUser?.username
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}