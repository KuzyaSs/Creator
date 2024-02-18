package ru.ermakov.creator.presentation.screen.following

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
import com.bumptech.glide.Glide
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.DialogDeletePostBinding
import ru.ermakov.creator.databinding.FragmentFollowingBinding
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.presentation.adapter.PostAdapter
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.shared.OptionsFragment
import ru.ermakov.creator.presentation.screen.shared.OptionsHandler
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

private const val THRESHOLD = 5

class FollowingFragment : Fragment(), OptionsHandler {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private var _dialogDeletePostBinding: DialogDeletePostBinding? = null

    @Inject
    lateinit var followingViewModelFactory: FollowingViewModelFactory
    private lateinit var followingViewModel: FollowingViewModel

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
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreatorActivity).showBottomNavigationView()
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        followingViewModel =
            ViewModelProvider(
                requireActivity(),
                followingViewModelFactory
            )[FollowingViewModel::class.java]
        optionsFragment = OptionsFragment(isEditShown = true, isDeleteShown = true)
        setUpSwipeRefreshLayout()
        setUpDeletePostDialog()
        setUpListeners()
        setUpObservers()
    }

    override fun onStart() {
        super.onStart()
        followingViewModel.updateSelectedPostId()
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
                followingViewModel.deleteSelectedPost()
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
                followingViewModel.refreshFollowingScreen()
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1) || recyclerViewPosts.canScrollVertically(-1)
            }

            cardViewTitle.setOnLongClickListener {
                recyclerViewPosts.smoothScrollToPosition(0)
                true
            }
            val feedFilterFragment = FeedFilterFragment()
            imageViewFilter.setOnClickListener {
                showFeedFilterFragment(feedFilterFragment = feedFilterFragment)
            }

            val accountFragment = AccountFragment()
            imageViewProfileAvatar.setOnClickListener {
                showAccountFragment(accountFragment = accountFragment)
            }
            imageViewFollows.setOnClickListener {
                showFollowsFragment()
            }
        }
    }

    private fun setUpObservers() {
        followingViewModel.followingUiState.observe(viewLifecycleOwner) { followingUiState ->
            followingUiState.apply {
                if (currentUser != null && postItems != null) {
                    setProfileAvatar(user = currentUser)
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
                followingViewModel.setSelectedPostId(postId = postItem.id)
                navigateToPostFragment(postId = postItem.id)
            }, onProfileAvatarClickListener = { postItem ->
                followingViewModel.setSelectedPostId(postId = postItem.id)
                navigateToBlogFragment(creatorId = postItem.creator.user.id)
            }, onMoreClickListener = { postItem ->
                followingViewModel.setSelectedPostId(postId = postItem.id)
                optionsFragment?.show(childFragmentManager, optionsFragment.toString())
            }, onSubscribeClickListener = { postItem ->
                followingViewModel.setSelectedPostId(postId = postItem.id)
                navigateToSubscriptionsFragment(creatorId = postItem.creator.user.id)
            }, onLikeClickListener = { postItem ->
                followingViewModel.insertLikeToPost(postId = postItem.id)
            }, onDislikeClickListener = { postItem ->
                followingViewModel.deleteLikeFromPost(postId = postItem.id)
            }, onCommentClickListener = { postItem ->
                followingViewModel.setSelectedPostId(postId = postItem.id)
                showToast("Comment")
            }
        )
        binding.recyclerViewPosts.adapter = postAdapter
        binding.recyclerViewPosts.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (layoutManager.itemCount - lastVisibleItemPosition <= THRESHOLD && recyclerView.scrollState != SCROLL_STATE_IDLE) {
                    followingViewModel.loadNextPostPage()
                }
            }
        })
    }

    private fun setProfileAvatar(user: User) {
        if (user.profileAvatarUrl.isNotEmpty()) {
            Glide.with(binding.root)
                .load(user.profileAvatarUrl)
                .placeholder(R.drawable.default_profile_avatar)
                .into(binding.imageViewProfileAvatar)
        }
    }

    private fun showFeedFilterFragment(feedFilterFragment: FeedFilterFragment) {
        if (!feedFilterFragment.isVisible) {
            feedFilterFragment.show(childFragmentManager, feedFilterFragment.toString())
        } else {
            feedFilterFragment.dismiss()
        }
    }

    private fun showAccountFragment(accountFragment: AccountFragment) {
        if (!accountFragment.isVisible) {
            accountFragment.show(childFragmentManager, accountFragment.toString())
        } else {
            accountFragment.dismiss()
        }
    }

    private fun showFollowsFragment() {
        val action = FollowingFragmentDirections.actionFollowingFragmentToFollowsFragment()
        findNavController().navigate(action)
    }

    private fun navigateToPostFragment(postId: Long) {
        showToast("navigateToPostFragment (post id: $postId)")
    }

    private fun navigateToBlogFragment(creatorId: String) {
        val action = FollowingFragmentDirections.actionFollowingFragmentToBlogFragment(
            creatorId = creatorId
        )
        findNavController().navigate(action)
    }

    private fun navigateToSubscriptionsFragment(creatorId: String) {
        val action = FollowingFragmentDirections.actionFollowingFragmentToSubscriptionsFragment(
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
            followingViewModel.clearErrorMessage()
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