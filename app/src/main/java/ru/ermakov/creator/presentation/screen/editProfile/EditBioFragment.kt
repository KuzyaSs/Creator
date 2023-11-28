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
import ru.ermakov.creator.databinding.FragmentEditBioBinding
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class EditBioFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentEditBioBinding? = null
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
        _binding = FragmentEditBioBinding.inflate(inflater, container, false)
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
            imageViewCheck.setOnClickListener { editBio() }
        }
    }

    private fun setUpObservers() {
        editProfileViewModel.editProfileUiState.observe(viewLifecycleOwner) { editProfileUiState ->
            editProfileUiState.apply {
                if (binding.textInputEditTextBio.text.isNullOrEmpty()) {
                    binding.textInputEditTextBio.setText(editProfileUiState.currentUser?.bio)
                }
                setErrorMessage(
                    errorMessage = editBioErrorMessage,
                    isErrorMessageShown = isEditBioErrorMessageShown
                )
            }
        }
    }

    private fun editBio() {
        val bio = binding.textInputEditTextBio.text?.trim().toString()
        editProfileViewModel.editBio(bio = bio)
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        binding.textViewErrorMessage.apply {
            text = textLocalizer.localizeText(text = errorMessage)
            isVisible = isErrorMessageShown
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        binding.textInputEditTextBio.setText(
            editProfileViewModel.editProfileUiState.value?.currentUser?.bio
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}