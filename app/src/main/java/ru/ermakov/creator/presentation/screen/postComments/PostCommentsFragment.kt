package ru.ermakov.creator.presentation.screen.postComments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        setUpCommentRecyclerView()
        setUpListeners()
        setUpObservers()
    }

    override fun onStart() {
        super.onStart()
        postCommentsViewModel.updateSelectedPostComment()
    }

    private fun setUpCommentRecyclerView() {
        postCommentAdapter = PostCommentAdapter(
            onMoreClickListener = { commentItem ->
                optionsFragment?.show(childFragmentManager, optionsFragment.toString())
                postCommentsViewModel.setSelectedPostCommentId(postCommentId = commentItem.id)
            },
            onProfileAvatarClickListener = { commentItem ->
                navigateToBlogScreen(creatorId = commentItem.user.id)
                postCommentsViewModel.setSelectedPostCommentId(postCommentId = commentItem.id)
            },
            onLikeClickListener = { commentItem ->
                postCommentsViewModel.setSelectedPostCommentId(postCommentId = commentItem.id)
                postCommentsViewModel.insertLikeToPostComment(postCommentId = commentItem.id)
            }, onDislikeClickListener = { commentItem ->
                postCommentsViewModel.setSelectedPostCommentId(postCommentId = commentItem.id)
                postCommentsViewModel.deleteLikeFromPostComment(commentId = commentItem.id)
            }, onReplyClickListener = { commentItem ->
                navigateToReplyCommentScreen(commentId = commentItem.id)
                postCommentsViewModel.setSelectedPostCommentId(postCommentId = commentItem.id)
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

    private fun setUpListeners() {
        binding.apply {
            textViewTitleWithBackButton.setOnClickListener {
                goBack()
            }
            textInputLayoutComment.setEndIconOnClickListener {
                sendComment()
            }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        postCommentsViewModel.postCommentsUiState.observe(viewLifecycleOwner) { postCommentsUiState ->
            postCommentsUiState.apply {
                if (postCommentItems != null) {
                    postCommentAdapter?.submitList(postCommentItems)
                    setEmptyCommentRecyclerViewInfo(isCommentRecyclerViewEmpty = postCommentItems.isEmpty())
                    setErrorMessage(
                        errorMessage = textLocalizer.localizeText(text = errorMessage),
                        isErrorMessageShown = isErrorMessageShown
                    )
                }
                binding.textInputLayoutComment.isVisible = postCommentItems != null
                binding.viewLoading.isVisible = postCommentItems == null
                binding.progressBarScreen.isVisible = postCommentItems == null && isLoadingShown
                binding.textViewScreenErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = postCommentItems == null && isErrorMessageShown
                }
                binding.imageViewScreenLogo.isVisible = postCommentItems == null && isErrorMessageShown
            }
        }
    }

    private fun sendComment() {

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

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
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
    }

    override fun edit() {
        postCommentsViewModel.setEditMessageMode(isEditMessageMode = true) // !
    }

    override fun delete() {
        postCommentsViewModel.deleteSelectedPostComment()
    }

    override fun close() {}
}