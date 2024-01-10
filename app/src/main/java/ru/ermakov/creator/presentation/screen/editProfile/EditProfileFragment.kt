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
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentEditProfileBinding
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var editProfileViewModelFactory: EditProfileViewModelFactory
    private lateinit var editProfileViewModel: EditProfileViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

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
        setUpSwipeRefreshLayout()
        setUpLoadingDialog()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpSwipeRefreshLayout() {
        binding.swipeRefreshLayout.apply {
            setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.backgroundColor
                )
            )
            setColorSchemeColors(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorAccent
                )
            )
        }
    }

    private fun registerMediaPicker() {
        mediaPicker = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia(),
        ) { uri ->
            if (uri != null) {
                editProfileViewModel.editProfileImage(
                    uri = uri,
                    fileName = uri.lastPathSegment.toString()
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
            swipeRefreshLayout.setOnRefreshListener {
                editProfileViewModel.refreshCurrentUser()
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1)
            }
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            textViewProfileAvatar.setOnClickListener { editProfileAvatar() }
            textViewProfileBackground.setOnClickListener { editProfileBackground() }
            val editUsernameFragment = EditUsernameFragment()
            textViewUsername.setOnClickListener {
                setEditUsernameFragment(editUsernameFragment = editUsernameFragment)
            }
            val editBioFragment = EditBioFragment()
            textViewBio.setOnClickListener {
                setEditBioFragment(editBioFragment = editBioFragment)
            }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        editProfileViewModel.editProfileUiState.observe(viewLifecycleOwner) { editProfileUiState ->
            editProfileUiState.apply {
                if (currentUser != null) {
                    setProfileAvatarAndBackground(user = currentUser)
                    setLoading(isLoadingShown = isLoadingShown)
                    setErrorMessage(
                        errorMessage = textLocalizer.localizeText(
                            text = editProfileErrorMessage
                        ),
                        isErrorMessageShown = isEditProfileErrorMessageShown
                    )
                }
                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = currentUser == null
                binding.progressBar.isVisible = !isEditProfileErrorMessageShown
                        && currentUser == null
                binding.textViewScreenErrorMessage.apply {
                    text = textLocalizer.localizeText(text = editProfileErrorMessage)
                    isVisible = isEditProfileErrorMessageShown && currentUser == null
                }
                binding.imageViewScreenLogo.isVisible = isEditProfileErrorMessageShown
                        && currentUser == null
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

    private fun editProfileAvatar() {
        editProfileViewModel.setEditProfileOptionToProfileAvatar()
        mediaPicker?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun editProfileBackground() {
        editProfileViewModel.setEditProfileOptionToProfileBackground()
        mediaPicker?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun setEditUsernameFragment(editUsernameFragment: EditUsernameFragment) {
        if (!editUsernameFragment.isVisible) {
            editUsernameFragment.show(childFragmentManager, editUsernameFragment.toString())
        } else {
            editUsernameFragment.dismiss()
        }
    }

    private fun setEditBioFragment(editBioFragment: EditBioFragment) {
        if (!editBioFragment.isVisible) {
            editBioFragment.show(childFragmentManager, editBioFragment.toString())
        } else {
            editBioFragment.dismiss()
        }
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setLoading(isLoadingShown: Boolean) {
        if (isLoadingShown) {
            loadingDialog?.show()
        } else {
            loadingDialog?.cancel()
        }
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        if (isErrorMessageShown) {
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            editProfileViewModel.clearEditProfileErrorMessage()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}