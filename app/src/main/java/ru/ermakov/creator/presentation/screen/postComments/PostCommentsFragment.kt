package ru.ermakov.creator.presentation.screen.postComments

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentPostCommentsBinding
import ru.ermakov.creator.presentation.adapter.PostCommentAdapter
import ru.ermakov.creator.presentation.screen.post.INVALID_POST_ID
import ru.ermakov.creator.presentation.screen.post.PostFragmentDirections
import ru.ermakov.creator.presentation.screen.post.PostViewModel
import ru.ermakov.creator.presentation.screen.post.PostViewModelFactory
import ru.ermakov.creator.presentation.screen.shared.OptionsFragment
import ru.ermakov.creator.presentation.screen.shared.OptionsHandler
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject


private const val THRESHOLD = 5

class PostCommentsFragment : BottomSheetDialogFragment(), OptionsHandler {
    private var _binding: FragmentPostCommentsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var postCommentsViewModelFactory: PostCommentsViewModelFactory
    private lateinit var postCommentsViewModel: PostCommentsViewModel

    @Inject
    lateinit var postViewModelFactory: PostViewModelFactory
    private lateinit var postViewModel: PostViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var postCommentAdapter: PostCommentAdapter? = null
    private var optionsFragment: OptionsFragment? = null
    private var loadingDialog: Dialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostCommentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        postCommentsViewModel = ViewModelProvider(
            requireParentFragment(),
            postCommentsViewModelFactory
        )[PostCommentsViewModel::class.java]
        postViewModel = ViewModelProvider(
            requireParentFragment(),
            postViewModelFactory
        )[PostViewModel::class.java]
        if (postCommentsViewModel.postCommentsUiState.value?.postCommentItems == null) {
            postCommentsViewModel.setPostCommentsScreen(
                postId = postViewModel.postUiState.value?.postItem?.id ?: INVALID_POST_ID
            )
        }
        optionsFragment = OptionsFragment(isEditShown = true, isDeleteShown = true)
        setUpSwipeRefreshLayout()
        setUpLoadingDialog()
        setUpBottomSheetBehavior()
        setUpEndButtonInCommentTextInputLayout()
        setUpListeners()
        setUpObservers()
    }

    override fun onStart() {
        super.onStart()
        postCommentsViewModel.updatePostComment()
    }

    private fun setUpSwipeRefreshLayout() {
        binding.swipeRefreshLayout.apply {
            setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(
                    requireContext(),
                    ru.ermakov.creator.R.color.backgroundColor
                )
            )
            setColorSchemeColors(
                ContextCompat.getColor(
                    requireContext(),
                    ru.ermakov.creator.R.color.colorAccent
                )
            )
        }
    }

    private fun setUpLoadingDialog() {
        loadingDialog = Dialog(requireContext())
        loadingDialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(ru.ermakov.creator.R.layout.dialog_loading)
        }
    }

    private fun setUpBottomSheetBehavior() {
        val bottomSheetBehavior = BottomSheetBehavior.from(view?.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun setUpEndButtonInCommentTextInputLayout() {
        val endIcon = binding.textInputLayoutComment.findViewById<View>(R.id.text_input_end_icon)
        val params = endIcon.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.BOTTOM
        endIcon.layoutParams = params
    }

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                postCommentsViewModel.refreshPostCommentScreen(
                    postId = postViewModel.postUiState.value?.postItem?.id ?: INVALID_POST_ID
                )
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                binding.scrollView.canScrollVertically(-1) ||
                        recyclerViewComments.canScrollVertically(-1)
            }
            textViewTitleWithBackButton.setOnClickListener {
                dismiss()
            }
            textInputEditTextComment.addTextChangedListener { text ->
                setUpEndIconTint(text = text.toString())
            }
            textInputLayoutComment.setEndIconOnClickListener {
                if (textInputEditTextComment.text.toString().isNotBlank()) {
                    if (postCommentsViewModel.postCommentsUiState.value?.isEditCommentMode == true) {
                        editComment()
                    } else {
                        sendComment()
                    }
                }
            }
            linearLayoutEditComment.setOnClickListener {
                binding.textInputEditTextComment.text?.clear()
                postCommentsViewModel.clearEditCommentMode()
            }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpEndIconTint(text: String) {
        if (text.isNotBlank()) {
            binding.textInputLayoutComment.setEndIconTintList(
                ColorStateList.valueOf(
                    resources.getColor(
                        ru.ermakov.creator.R.color.colorAccent
                    )
                )
            )
        } else {
            binding.textInputLayoutComment.setEndIconTintList(
                ColorStateList.valueOf(
                    resources.getColor(
                        ru.ermakov.creator.R.color.iconTint
                    )
                )
            )
        }
    }

    private fun setUpObservers() {
        postCommentsViewModel.postCommentsUiState.observe(viewLifecycleOwner) { postCommentsUiState ->
            postCommentsUiState.apply {
                if (postCommentItems != null) {
                    if (postCommentAdapter == null) {
                        setUpCommentRecyclerView(userId = currentUserId)
                    }
                    postCommentAdapter?.submitList(postCommentItems)
                    setLoadingDialog(isLoadingShown = isLoadingDialogShown)
                    setEmptyCommentRecyclerViewInfo(isCommentRecyclerViewEmpty = postCommentItems.isEmpty())
                    setErrorMessage(
                        errorMessage = textLocalizer.localizeText(text = errorMessage),
                        isErrorMessageShown = isErrorMessageShown
                    )

                    binding.linearLayoutEditComment.isVisible = isEditCommentMode
                    if (isEditCommentMode) {
                        val content = binding.textInputEditTextComment.text?.trim().toString()
                        if (content.isBlank()) {
                            binding.textInputEditTextComment.setText(editedPostCommentItem?.content)
                        }
                    }
                    if (isPostCommentSent) {
                        binding.textInputEditTextComment.text?.clear()
                        binding.recyclerViewComments.smoothScrollToPosition(0)
                        postCommentsViewModel.clearIsPostCommentSent()
                    }
                    if (isPostCommentEdited) {
                        binding.textInputEditTextComment.text?.clear()
                        postCommentsViewModel.clearIsPostCommentEdited()
                    }
                }
                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.textInputLayoutComment.isVisible = postCommentItems != null
                binding.viewLoading.isVisible = postCommentItems == null
                binding.progressBarScreen.isVisible = postCommentItems == null && isLoadingShown
                binding.textViewScreenErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = postCommentItems == null && isErrorMessageShown
                }
                binding.imageViewScreenLogo.isVisible =
                    postCommentItems == null && isErrorMessageShown
            }
        }
    }

    private fun setUpCommentRecyclerView(userId: String) {
        postCommentAdapter = PostCommentAdapter(
            userId = userId,
            onMoreClickListener = { commentItem ->
                optionsFragment?.show(childFragmentManager, optionsFragment.toString())
                postCommentsViewModel.setSelectedPostComment(postComment = commentItem)
            },
            onProfileAvatarClickListener = { commentItem ->
                navigateToBlogScreen(creatorId = commentItem.user.id)
                postCommentsViewModel.setSelectedPostComment(postComment = commentItem)
            },
            onLikeClickListener = { commentItem ->
                postCommentsViewModel.setSelectedPostComment(postComment = commentItem)
                postCommentsViewModel.insertLikeToPostComment(postCommentId = commentItem.id)
            }, onDislikeClickListener = { commentItem ->
                postCommentsViewModel.setSelectedPostComment(postComment = commentItem)
                postCommentsViewModel.deleteLikeFromPostComment(postCommentId = commentItem.id)
            }, onReplyClickListener = { commentItem ->
                navigateToReplyCommentScreen(commentId = commentItem.id)
                postCommentsViewModel.setSelectedPostComment(postComment = commentItem)
            }
        )
        binding.recyclerViewComments.adapter = postCommentAdapter
        binding.recyclerViewComments.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (layoutManager.itemCount - lastVisibleItemPosition <= THRESHOLD && recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                    postCommentsViewModel.loadNextPostCommentPage(
                        postId = postViewModel.postUiState.value?.postItem?.id ?: INVALID_POST_ID
                    )
                }
            }
        })
    }

    private fun sendComment() {
        val postId = postViewModel.postUiState.value?.postItem?.id ?: INVALID_POST_ID
        val content = binding.textInputEditTextComment.text?.trim().toString()
        postCommentsViewModel.sendPostComment(
            postId = postId,
            content = content
        )
    }

    private fun editComment() {
        val postId = postViewModel.postUiState.value?.postItem?.id ?: INVALID_POST_ID
        val content = binding.textInputEditTextComment.text?.trim().toString()
        postCommentsViewModel.editPostComment(
            postId = postId,
            content = content
        )
    }

    private fun setLoadingDialog(isLoadingShown: Boolean) {
        if (isLoadingShown) {
            loadingDialog?.show()
        } else {
            loadingDialog?.cancel()
        }
    }

    private fun setEmptyCommentRecyclerViewInfo(isCommentRecyclerViewEmpty: Boolean) {
        binding.imageViewCommentsLogo.isVisible = isCommentRecyclerViewEmpty
        binding.textViewEmptyListMessage.isVisible = isCommentRecyclerViewEmpty
    }

    private fun navigateToBlogScreen(creatorId: String) {
        val action = PostFragmentDirections.actionPostFragmentToBlogFragment(
            creatorId = creatorId
        )
        findNavController().navigate(action)
    }

    private fun navigateToReplyCommentScreen(commentId: Long) {

    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        if (isErrorMessageShown) {
            showToast(message = errorMessage)
            postCommentsViewModel.clearErrorMessage()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        postCommentAdapter = null
    }

    override fun edit() {
        binding.textInputEditTextComment.text?.clear()
        postCommentsViewModel.setEditedPostComment()
    }

    override fun delete() {
        postCommentsViewModel.deleteSelectedPostComment()
    }

    override fun close() {}
}