package ru.ermakov.creator.presentation.screen.editProfile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentEditProfileBinding
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.presentation.State
import ru.ermakov.creator.presentation.exception.ExceptionLocalizer
import javax.inject.Inject

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var editProfileViewModelFactory: EditProfileViewModelFactory
    private lateinit var editProfileViewModel: EditProfileViewModel

    @Inject
    lateinit var exceptionLocalizer: ExceptionLocalizer

    private var mediaPicker: ActivityResultLauncher<PickVisualMediaRequest>? = null
    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerMediaPicker()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        editProfileViewModel =
            ViewModelProvider(this, editProfileViewModelFactory)[EditProfileViewModel::class.java]
        setUpLoadingDialog()
        setUpListeners()
        setUpObservers()
    }

    private fun registerMediaPicker() {
        mediaPicker = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia(),
        ) { uri ->
            if (uri != null) {
                editProfileViewModel.uploadFile(
                    uri = uri,
                    name = uri.lastPathSegment.toString()
                )
            }
        }
    }

    private fun setUpLoadingDialog() {
        loadingDialog = Dialog(requireContext())
        loadingDialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.dialog_loading)
            setOnDismissListener { editProfileViewModel.cancelUploadTask() }
        }
    }

    private fun setUpListeners() {
        binding.apply {
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            textViewProfileAvatar.setOnClickListener { showEditProfileAvatarFragment() }
            imageViewProfileBackground.setOnClickListener { showEditProfileBackgroundFragment() }
            textViewUsername.setOnClickListener { showEditUsernameFragment() }
            textViewAbout.setOnClickListener { showEditAboutFragment() }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        editProfileViewModel.editProfileUiState.observe(viewLifecycleOwner) { editProfileUiState ->
            when (editProfileUiState.state) {
                State.INITIAL -> {}
                State.SUCCESS -> {
                    hideProgressBar()
                    hideLoadingView()
                    hideLoadingDialog()
                    setProfileAvatarAndBackground(editProfileUiState.currentUser!!)
                }

                State.ERROR -> {
                    hideProgressBar()
                    hideLoadingDialog()
                    val errorMessage = exceptionLocalizer.localizeException(
                        errorMessage = editProfileUiState.errorMessage
                    )
                    showToast(errorMessage)
                }

                State.LOADING -> {
                    hideError()
                    if (editProfileUiState.currentUser == null) {
                        showProgressBar()
                        showLoadingView()
                    }
                    if (editProfileUiState.isFileUploading) {
                        showLoadingDialog()
                    }
                }
            }
        }
    }

    private fun setProfileAvatarAndBackground(user: User) {
        if (user.profileAvatarUrl.isNotEmpty()) {
            Glide.with(binding.root)
                .load(user.profileAvatarUrl)
                .placeholder(R.drawable.default_profile_avatar)
                .into(binding.imageViewProfileAvatar)
        }
        if (user.profileBackgroundUrl.isNotEmpty()) {
            Glide.with(binding.root)
                .load(user.profileBackgroundUrl)
                .placeholder(R.drawable.default_profile_background)
                .into(binding.imageViewProfileBackground)
        }
    }

    private fun showEditProfileAvatarFragment() {
        mediaPicker?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showEditProfileBackgroundFragment() {

    }

    private fun showEditUsernameFragment() {

    }

    private fun showEditAboutFragment() {

    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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
        binding.progressBar.isVisible = true
    }

    private fun hideProgressBar() {
        binding.progressBar.isVisible = false
    }

    // Hide content during loading screen.
    private fun showLoadingView() {
        binding.viewLoading.isVisible = true
    }

    // Show content after loading screen.
    private fun hideLoadingView() {
        binding.viewLoading.isVisible = false
    }

    // Show loading dialog during uploading file.
    private fun showLoadingDialog() {
        loadingDialog?.show()
    }

    // Hide loading dialog after uploading file.
    private fun hideLoadingDialog() {
        loadingDialog?.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}