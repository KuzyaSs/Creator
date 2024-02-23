package ru.ermakov.creator.presentation.screen.search.searchPost

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.DialogDeletePostBinding
import ru.ermakov.creator.databinding.FragmentSearchPostBinding
import ru.ermakov.creator.presentation.adapter.PostAdapter
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.search.SearchFragmentDirections
import ru.ermakov.creator.presentation.screen.shared.OptionsFragment
import ru.ermakov.creator.presentation.screen.shared.OptionsHandler
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

private const val THRESHOLD = 5

class SearchPostFragment : Fragment(), OptionsHandler {
    private var _binding: FragmentSearchPostBinding? = null
    private val binding get() = _binding!!
    private var _dialogDeletePostBinding: DialogDeletePostBinding? = null

    @Inject
    lateinit var searchPostViewModelFactory: SearchPostViewModelFactory
    private lateinit var searchPostViewModel: SearchPostViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var postAdapter: PostAdapter? = null
    private var deletePostDialog: Dialog? = null
    private var optionsFragment: OptionsFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreatorActivity).showBottomNavigationView()
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        searchPostViewModel = ViewModelProvider(
            requireActivity(),
            searchPostViewModelFactory
        )[SearchPostViewModel::class.java]
        optionsFragment = OptionsFragment(isEditShown = true, isDeleteShown = true)
        setUpDeletePostDialog()
        setUpObservers()
    }

    override fun onStart() {
        super.onStart()
        searchPostViewModel.updateSelectedPostId()
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
                searchPostViewModel.deleteSelectedPost()
                deletePostDialog?.dismiss()
            }
        }
    }

    private fun setUpObservers() {
        searchPostViewModel.searchPostUiState.observe(viewLifecycleOwner) { searchPostUiState ->
            searchPostUiState.apply {
                if (postItems != null) {
                    if (postAdapter == null) {
                        setUpPostRecyclerView(userId = currentUserId)
                    }
                    postAdapter?.submitList(postItems)
                }
                setNoResultsFound(
                    isNoResultsFoundShown = postItems.isNullOrEmpty() && lastSearchQuery.isNotBlank() && !isLoadingShown
                )
                binding.progressBar.isVisible = isLoadingShown && postItems.isNullOrEmpty()
                setErrorMessage(
                    errorMessage = textLocalizer.localizeText(text = errorMessage),
                    isErrorMessageShown = isErrorMessageShown
                )
            }
        }
    }

    private fun setUpPostRecyclerView(userId: String) {
        postAdapter = PostAdapter(
            userId = userId,
            onItemClickListener = { postItem ->
                searchPostViewModel.setSelectedPostId(postId = postItem.id)
                navigateToPostFragment(postId = postItem.id)
            }, onProfileAvatarClickListener = { postItem ->
                searchPostViewModel.setSelectedPostId(postId = postItem.id)
                navigateToBlogFragment(creatorId = postItem.creator.user.id)
            }, onMoreClickListener = { postItem ->
                searchPostViewModel.setSelectedPostId(postId = postItem.id)
                optionsFragment?.show(childFragmentManager, optionsFragment.toString())
            }, onSubscribeClickListener = { postItem ->
                searchPostViewModel.setSelectedPostId(postId = postItem.id)
                navigateToSubscriptionsFragment(creatorId = postItem.creator.user.id)
            }, onLikeClickListener = { postItem ->
                searchPostViewModel.insertLikeToPost(postId = postItem.id)
            }, onDislikeClickListener = { postItem ->
                searchPostViewModel.deleteLikeFromPost(postId = postItem.id)
            }, onCommentClickListener = { postItem ->
                searchPostViewModel.setSelectedPostId(postId = postItem.id)
                showToast("Comment")
            }
        )
        binding.recyclerViewPosts.adapter = postAdapter
        binding.recyclerViewPosts.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (layoutManager.itemCount - lastVisibleItemPosition <= THRESHOLD && recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                    (parentFragment as PostPageHandler).loadNextPostPage()
                }
            }
        })
    }

    private fun navigateToPostFragment(postId: Long) {
        showToast("navigateToPostFragment (post id: $postId)")
    }

    private fun navigateToBlogFragment(creatorId: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToBlogFragment(
            creatorId = creatorId
        )
        findNavController().navigate(action)
    }

    private fun navigateToSubscriptionsFragment(creatorId: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToSubscriptionsFragment(
            creatorId = creatorId
        )
        findNavController().navigate(action)
    }

    private fun setNoResultsFound(isNoResultsFoundShown: Boolean) {
        binding.imageViewLogo.isVisible = isNoResultsFoundShown
        binding.textViewNoResultsFound.isVisible = isNoResultsFoundShown
        binding.textViewNoResultsFoundDescription.isVisible = isNoResultsFoundShown
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        if (isErrorMessageShown) {
            showToast(message = errorMessage)
            searchPostViewModel.clearErrorMessage()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        postAdapter = null
    }

    override fun edit() {
        // Navigate to edit post screen.
        showToast("*Edit post*")
        // navigateToPostFragment(postId = *)
    }

    override fun delete() {
        deletePostDialog?.show()
    }

    override fun close() {}
}