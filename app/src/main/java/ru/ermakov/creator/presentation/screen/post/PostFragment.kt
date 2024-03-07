package ru.ermakov.creator.presentation.screen.post

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.DialogDeletePostBinding
import ru.ermakov.creator.databinding.FragmentPostBinding
import ru.ermakov.creator.domain.model.PostItem
import ru.ermakov.creator.presentation.adapter.PostSubscriptionAdapter
import ru.ermakov.creator.presentation.adapter.PostTagAdapter
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.postComments.PostCommentsFragment
import ru.ermakov.creator.presentation.screen.shared.OptionsFragment
import ru.ermakov.creator.presentation.screen.shared.OptionsHandler
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class PostFragment : Fragment(), OptionsHandler {
    private val arguments: PostFragmentArgs by navArgs()
    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!
    private var _dialogDeletePostBinding: DialogDeletePostBinding? = null

    @Inject
    lateinit var postViewModelFactory: PostViewModelFactory
    private lateinit var postViewModel: PostViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var postTagAdapter: PostTagAdapter? = null
    private var postSubscriptionAdapter: PostSubscriptionAdapter? = null
    private var deletePostDialog: Dialog? = null
    private var optionsFragment: OptionsFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreatorActivity).hideBottomNavigationView()
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        postViewModel = ViewModelProvider(this, postViewModelFactory)[PostViewModel::class.java]
        if (postViewModel.postUiState.value?.postItem == null) {
            postViewModel.setPostScreen(postId = arguments.postId)
        }
        optionsFragment = OptionsFragment(isEditShown = true, isDeleteShown = true)
        setUpSwipeRefreshLayout()
        setUpDeletePostDialog()
        setUpTagRecyclerView()
        setUpSubscriptionRecyclerView()
        setUpListeners()
        setUpObservers()
    }

    override fun onStart() {
        super.onStart()
        postViewModel.updatePost(postId = arguments.postId)
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

    private fun setUpDeletePostDialog() {
        _dialogDeletePostBinding = DialogDeletePostBinding.inflate(layoutInflater)
        deletePostDialog = Dialog(requireContext())
        deletePostDialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(_dialogDeletePostBinding?.root!!)
            setCancelable(false)
            _dialogDeletePostBinding?.textViewNo?.setOnClickListener {
                deletePostDialog?.dismiss()
            }
            _dialogDeletePostBinding?.textViewYes?.setOnClickListener {
                postViewModel.deletePost(postId = arguments.postId)
                deletePostDialog?.dismiss()
            }
        }
    }

    private fun setUpTagRecyclerView() {
        postTagAdapter = PostTagAdapter(onChangeClickListener = {})
        binding.recyclerViewTags.adapter = postTagAdapter
    }

    private fun setUpSubscriptionRecyclerView() {
        postSubscriptionAdapter = PostSubscriptionAdapter(onChangeClickListener = {})
        binding.recyclerViewRequiredSubscriptions.adapter = postSubscriptionAdapter
    }

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                postViewModel.refreshPostScreen(postId = arguments.postId)
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                binding.scrollView.canScrollVertically(-1)
            }

            textViewTitleWithBackButton.setOnClickListener { goBack() }

            imageViewCreatorProfileAvatar.setOnClickListener {
                navigateToBlogScreen()
            }

            imageViewMore.setOnClickListener {
                optionsFragment?.show(childFragmentManager, optionsFragment.toString())
            }

            buttonSubscribe.setOnClickListener {
                navigateToSubscriptionsScreen()
            }

            imageViewLike.setOnClickListener {
                if (postViewModel.postUiState.value?.postItem?.isLiked == true) {
                    postViewModel.deleteLikeFromPost(postId = arguments.postId)
                } else {
                    postViewModel.insertLikeToPost(postId = arguments.postId)
                }
            }

            val postCommentFragment = PostCommentsFragment()
            imageViewComment.setOnClickListener {
                showCommentsScreen(postCommentFragment = postCommentFragment)
            }

            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        postViewModel.postUiState.observe(viewLifecycleOwner) { postUiState ->
            postUiState.apply {
                if (postItem != null) {
                    if (isPostDeleted) {
                        goBack()
                    }
                    val isOwner = currentUserId == postItem.creator.user.id
                    setPost(isOwner = isOwner, postItem = postItem)
                    setErrorMessage(
                        errorMessage = textLocalizer.localizeText(text = errorMessage),
                        isErrorMessageShown = isErrorMessageShown
                    )
                }
                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = postItem == null
                binding.progressBar.isVisible = isLoadingShown && postItem == null
                binding.textViewScreenErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = isErrorMessageShown && postItem == null
                }
                binding.imageViewScreenLogo.isVisible = isErrorMessageShown && postItem == null
            }
        }
    }

    private fun setPost(isOwner: Boolean, postItem: PostItem) {
        binding.apply {
            Glide.with(root)
                .load(postItem.creator.user.profileAvatarUrl)
                .placeholder(R.drawable.default_profile_avatar)
                .into(imageViewCreatorProfileAvatar)
            textViewCreatorName.text = postItem.creator.user.username
            textViewPublicationDate.text = postItem.publicationDate
            textViewTitle.text = postItem.title
            textViewContent.text = postItem.content
            textViewContent.isVisible = postItem.isAvailable

            // viewPagerImages - later...
            postTagAdapter?.submitList(postItem.tags)
            postSubscriptionAdapter?.submitList(postItem.requiredSubscriptions)

            textViewIsEdited.isVisible = postItem.isEdited
            constraintLayoutIsNotAvailable.isVisible = !postItem.isAvailable
            imageViewMore.isVisible = isOwner

            textViewLikeCount.text = postItem.likeCount.toString()
            textViewLikeCount.isVisible = postItem.isAvailable
            if (postItem.isLiked) {
                imageViewLike.setImageResource(R.drawable.ic_favorite_filled_accent)
            } else {
                imageViewLike.setImageResource(R.drawable.ic_favorite)
            }
            imageViewLike.isVisible = postItem.isAvailable

            textViewCommentCount.text = postItem.commentCount.toString()
            textViewCommentCount.isVisible = postItem.isAvailable
            imageViewComment.isVisible = postItem.isAvailable
            textViewIsEdited.isVisible = postItem.isAvailable
        }
    }

    private fun showCommentsScreen(postCommentFragment: PostCommentsFragment) {
        if (!postCommentFragment.isVisible) {
            postCommentFragment.show(childFragmentManager, postCommentFragment.toString())
        } else {
            postCommentFragment.dismiss()
        }
    }

    private fun navigateToBlogScreen() {
        val action = PostFragmentDirections.actionPostFragmentToBlogFragment(
            creatorId = postViewModel.postUiState.value?.postItem?.creator?.user?.id ?: ""
        )
        findNavController().navigate(action)
    }

    private fun navigateToSubscriptionsScreen() {
        val action = PostFragmentDirections.actionPostFragmentToSubscriptionsFragment(
            creatorId = postViewModel.postUiState.value?.postItem?.creator?.user?.id ?: ""
        )
        findNavController().navigate(action)
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        if (isErrorMessageShown) {
            showToast(message = errorMessage)
            postViewModel.clearErrorMessage()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun edit() {
        val action = PostFragmentDirections.actionPostFragmentToEditPostFragment(
            creatorId = postViewModel.postUiState.value?.postItem?.creator?.user?.id ?: "",
            postId = arguments.postId
        )
        findNavController().navigate(action)
    }

    override fun delete() {
        deletePostDialog?.show()
    }

    override fun close() {}
}