package ru.ermakov.creator.presentation.screen.editProfile

import android.content.ContentResolver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
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

    private lateinit var mediaPicker: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPicker = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia(),
        ) { uri ->
            if (uri != null) {
                editProfileViewModel.uploadProfileAvatar(
                    uri = uri,
                    name = uri.lastPathSegment.toString()
                )
            }
        }
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
        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.apply {
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            textViewProfileAvatar.setOnClickListener { showEditProfileAvatarFragment() }
            imageViewProfileBackground.setOnClickListener { showEditProfileBackgroundFragment() }
            textViewUsername.setOnClickListener { showEditUsernameFragment() }
            textViewAbout.setOnClickListener { showEditAboutFragment() }
        }
    }

    private fun setUpObservers() {
        editProfileViewModel.editProfileUiState.observe(viewLifecycleOwner) { editProfileUiState ->
            when (editProfileUiState.state) {
                State.INITIAL -> {}
                State.SUCCESS -> {
                    hideProgressBar()
                    hideLoadingView()
                    setProfileAvatarAndBackground(editProfileUiState.currentUser!!)
                }

                State.ERROR -> {
                    hideProgressBar()
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
                }
            }
        }
    }

    private fun setProfileAvatarAndBackground(user: User) {
        if (user.profileAvatarUrl.isNotEmpty()) {
            Glide.with(binding.root)
                .load(user.profileAvatarUrl)
                .into(binding.imageViewProfileAvatar)
        }
        if (user.profileBackgroundUrl.isNotEmpty()) {
            Glide.with(binding.root)
                .load(user.profileBackgroundUrl)
                .into(binding.imageViewProfileBackground)
        }
    }

    private fun showEditProfileAvatarFragment() {
        mediaPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
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

    private fun showLoadingView() {
        binding.viewLoading.isVisible = true
    }

    private fun hideLoadingView() {
        binding.viewLoading.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}