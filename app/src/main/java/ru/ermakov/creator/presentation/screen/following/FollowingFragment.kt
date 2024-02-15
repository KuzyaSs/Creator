package ru.ermakov.creator.presentation.screen.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import ru.ermakov.creator.databinding.FragmentFollowingBinding
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.presentation.adapter.PostAdapter
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

private const val THRESHOLD = 5

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var followingViewModelFactory: FollowingViewModelFactory
    private lateinit var followingViewModel: FollowingViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var postAdapter: PostAdapter? = null

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
        setUpSwipeRefreshLayout()
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

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                followingViewModel.refreshFollowingScreen()
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1) || recyclerViewPosts.canScrollVertically(-1)
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
                navigateToPostFragment(postId = postItem.id)
            }, onProfileAvatarClickListener = { creator ->
                navigateToBlogFragment(creatorId = creator.user.id)
            }, onMoreClickListener = { postItem ->
                showToast("More")
            }, onLikeClickListener = { postItem ->
                showToast("Like")
            }, onCommentClickListener = { postItem ->
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

    private fun navigateToBlogFragment(creatorId: String) {
        showToast("navigateToBlogFragment (creator id: $creatorId)")
    }

    private fun navigateToPostFragment(postId: Long) {
        showToast("navigateToPostFragment (post id: $postId)")
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
}