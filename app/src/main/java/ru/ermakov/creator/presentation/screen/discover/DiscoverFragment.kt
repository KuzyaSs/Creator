package ru.ermakov.creator.presentation.screen.discover

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.DialogDeletePostBinding
import ru.ermakov.creator.databinding.FragmentDiscoverBinding
import ru.ermakov.creator.presentation.adapter.PostAdapter
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.discover.discoverFeedFilter.DiscoverFeedFilterFragment
import ru.ermakov.creator.presentation.screen.shared.OptionsFragment
import ru.ermakov.creator.presentation.screen.shared.OptionsHandler
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

private const val THRESHOLD = 5

class DiscoverFragment : Fragment(), OptionsHandler {
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private var _dialogDeletePostBinding: DialogDeletePostBinding? = null

    @Inject
    lateinit var discoverViewModelFactory: DiscoverViewModelFactory
    private lateinit var discoverViewModel: DiscoverViewModel

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
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreatorActivity).showBottomNavigationView()
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        discoverViewModel =
            ViewModelProvider(
                requireActivity(),
                discoverViewModelFactory
            )[DiscoverViewModel::class.java]
        optionsFragment = OptionsFragment(isEditShown = true, isDeleteShown = true)
        setUpSwipeRefreshLayout()
        setUpDeletePostDialog()
        setUpListeners()
        setUpObservers()
    }

    override fun onStart() {
        super.onStart()
        discoverViewModel.updateSelectedPostId()
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
                discoverViewModel.deleteSelectedPost()
                deletePostDialog?.dismiss()
            }
        }
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

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                discoverViewModel.refreshDiscoverScreen()
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1) || recyclerViewPosts.canScrollVertically(-1)
            }

            cardViewTitle.setOnLongClickListener {
                recyclerViewPosts.smoothScrollToPosition(0)
                true
            }
            val discoverFeedFilterFragment = DiscoverFeedFilterFragment()
            imageViewFilter.setOnClickListener {
                showDiscoverFeedFilterFragment(discoverFeedFilterFragment = discoverFeedFilterFragment)
            }
        }
    }

    private fun setUpObservers() {
        discoverViewModel.discoverUiState.observe(viewLifecycleOwner) { discoverUiState ->
            discoverUiState.apply {
                if (currentUser != null && postItems != null) {
                    if (postAdapter == null) {
                        setUpPostRecyclerView(userId = currentUser.id)
                    }
                    postAdapter?.submitList(postItems)
                }
                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                setUpPostRecyclerViewState(
                    isPostRecyclerViewEmpty = postItems.isNullOrEmpty(),
                    isLoading = isLoadingShown
                )
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
                discoverViewModel.setSelectedPostId(postId = postItem.id)
                navigateToPostFragment(postId = postItem.id)
            }, onProfileAvatarClickListener = { postItem ->
                discoverViewModel.setSelectedPostId(postId = postItem.id)
                navigateToBlogFragment(creatorId = postItem.creator.user.id)
            }, onMoreClickListener = { postItem ->
                discoverViewModel.setSelectedPostId(postId = postItem.id)
                optionsFragment?.show(childFragmentManager, optionsFragment.toString())
            }, onSubscribeClickListener = { postItem ->
                discoverViewModel.setSelectedPostId(postId = postItem.id)
                navigateToSubscriptionsFragment(creatorId = postItem.creator.user.id)
            }, onLikeClickListener = { postItem ->
                discoverViewModel.insertLikeToPost(postId = postItem.id)
            }, onDislikeClickListener = { postItem ->
                discoverViewModel.deleteLikeFromPost(postId = postItem.id)
            }, onCommentClickListener = { postItem ->
                discoverViewModel.setSelectedPostId(postId = postItem.id)
                navigateToPostFragment(postId = postItem.id)
            }
        )
        binding.recyclerViewPosts.adapter = postAdapter
        binding.recyclerViewPosts.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (layoutManager.itemCount - lastVisibleItemPosition <= THRESHOLD && recyclerView.scrollState != SCROLL_STATE_IDLE) {
                    discoverViewModel.loadNextPostPage()
                }
            }
        })
    }

    private fun showDiscoverFeedFilterFragment(discoverFeedFilterFragment: DiscoverFeedFilterFragment) {
        if (!discoverFeedFilterFragment.isVisible) {
            discoverFeedFilterFragment.show(
                childFragmentManager,
                discoverFeedFilterFragment.toString()
            )
        } else {
            discoverFeedFilterFragment.dismiss()
        }
    }

    private fun navigateToPostFragment(postId: Long) {
        val action = DiscoverFragmentDirections.actionDiscoverFragmentToPostFragment(
            postId = postId
        )
        findNavController().navigate(action)
    }

    private fun navigateToBlogFragment(creatorId: String) {
        val action = DiscoverFragmentDirections.actionDiscoverFragmentToBlogFragment(
            creatorId = creatorId
        )
        findNavController().navigate(action)
    }

    private fun navigateToSubscriptionsFragment(creatorId: String) {
        val action = DiscoverFragmentDirections.actionDiscoverFragmentToSubscriptionsFragment(
            creatorId = creatorId
        )
        findNavController().navigate(action)
    }

    private fun setUpPostRecyclerViewState(isPostRecyclerViewEmpty: Boolean, isLoading: Boolean) {
        binding.imageViewLogo.isVisible = isPostRecyclerViewEmpty && !isLoading
        binding.textViewEmptyListMessage.isVisible = isPostRecyclerViewEmpty && !isLoading
        binding.progressBarRecyclerViewPosts.isVisible = isPostRecyclerViewEmpty && isLoading
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        if (isErrorMessageShown) {
            showToast(message = errorMessage)
            discoverViewModel.clearErrorMessage()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
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