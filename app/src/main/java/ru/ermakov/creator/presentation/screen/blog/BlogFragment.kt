package ru.ermakov.creator.presentation.screen.blog

import android.app.Dialog
import android.content.res.ColorStateList
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.DialogDeletePostBinding
import ru.ermakov.creator.databinding.FragmentBlogBinding
import ru.ermakov.creator.domain.model.Creator
import ru.ermakov.creator.presentation.adapter.PostAdapter
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.blog.blogFilter.BlogFilterFragment
import ru.ermakov.creator.presentation.screen.shared.OptionsFragment
import ru.ermakov.creator.presentation.screen.shared.OptionsHandler
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

private const val THRESHOLD = 5

class BlogFragment : Fragment(), OptionsHandler {
    private val arguments: BlogFragmentArgs by navArgs()
    private var _binding: FragmentBlogBinding? = null
    private val binding get() = _binding!!
    private var _dialogDeletePostBinding: DialogDeletePostBinding? = null

    @Inject
    lateinit var blogViewModelFactory: BlogViewModelFactory
    private lateinit var blogViewModel: BlogViewModel

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
        _binding = FragmentBlogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreatorActivity).hideBottomNavigationView()
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        blogViewModel = ViewModelProvider(this, blogViewModelFactory)[BlogViewModel::class.java]
        if (blogViewModel.blogUiState.value?.creator == null) {
            blogViewModel.setBlogScreen(creatorId = arguments.creatorId)
        }
        optionsFragment = OptionsFragment(isEditShown = true, isDeleteShown = true)
        setUpSwipeRefreshLayout()
        setUpDeletePostDialog()
        setUpListeners()
        setUpObservers()
    }

    override fun onStart() {
        super.onStart()
        blogViewModel.updateSelectedPostId()
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
                blogViewModel.deleteSelectedPost()
                deletePostDialog?.dismiss()
            }
        }
    }

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                blogViewModel.refreshBlogScreen(arguments.creatorId)
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                binding.scrollView.canScrollVertically(-1) || recyclerViewPosts.canScrollVertically(
                    -1
                )
            }

            textViewTitleWithBackButton.setOnClickListener { goBack() }

            val creatorBioFragment = CreatorBioFragment()
            textViewAbout.setOnClickListener {
                setBioFragment(creatorBioFragment = creatorBioFragment)
            }

            textViewGoals.setOnClickListener {
                navigateToGoalFragment()
            }

            textViewTip.setOnClickListener {
                navigateToTipFragment()
            }

            textViewSubscriptions.setOnClickListener {
                navigateToSubscriptionsFragment()
            }

            buttonFollow.setOnClickListener {
                if (blogViewModel.blogUiState.value?.isFollower == true) {
                    blogViewModel.unfollow()
                } else {
                    blogViewModel.follow()
                }
            }

            buttonSubscribe.setOnClickListener {
                navigateToSubscriptionsFragment()
            }

            buttonPublish.setOnClickListener {
                navigateToPublishFragment()
            }

            val blogFilterFragment = BlogFilterFragment()
            imageViewFilter.setOnClickListener {
                showBlogFilterFragment(blogFilterFragment = blogFilterFragment)
            }

            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        blogViewModel.blogUiState.observe(viewLifecycleOwner) { blogUiState ->
            blogUiState.apply {
                if (creator != null && postItems != null) {
                    val isOwner = currentUserId == creator.user.id
                    setUpActionVisibility(
                        isOwner = isOwner,
                        isFollower = isFollower,
                        isSubscriber = isSubscriber
                    )
                    setCreator(creator = creator)

                    if (postAdapter == null) {
                        setUpPostRecyclerView(userId = currentUserId)
                    }
                    postAdapter?.submitList(postItems)
                    setEmptyPostRecyclerViewInfo(isPostRecyclerViewEmpty = postItems.isEmpty())
                    setErrorMessage(
                        errorMessage = textLocalizer.localizeText(text = errorMessage),
                        isErrorMessageShown = isErrorMessageShown
                    )
                    binding.recyclerViewPosts.layoutParams.height = 2000
                    binding.recyclerViewPosts.requestLayout()
                }
                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = creator == null
                binding.progressBar.isVisible = isLoadingShown && creator == null
                binding.textViewScreenErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = isErrorMessageShown && creator == null
                }
                binding.imageViewScreenLogo.isVisible = isErrorMessageShown && creator == null
            }
        }
    }

    private fun setUpActionVisibility(
        isOwner: Boolean,
        isFollower: Boolean,
        isSubscriber: Boolean
    ) {
        binding.textViewTip.isVisible = !isOwner
        binding.textViewSubscriptions.isVisible = isOwner
        setFollowButton(isFollower = isFollower, isOwner = isOwner)
        setSubscribeButton(isSubscriber = isSubscriber, isOwner = isOwner)
        binding.buttonPublish.visibility = if (isOwner) View.VISIBLE else View.INVISIBLE
    }

    private fun setFollowButton(isFollower: Boolean, isOwner: Boolean) {
        binding.buttonFollow.apply {
            isVisible = !isOwner
            if (isFollower) {
                setSecondaryButton(this)
                text = resources.getString(R.string.followed)
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_filled)
            } else {
                setPrimaryButton(this)
                text = resources.getString(R.string.follow)
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite)
            }
        }
    }

    private fun setSubscribeButton(isSubscriber: Boolean, isOwner: Boolean) {
        binding.buttonSubscribe.apply {
            isVisible = !isOwner
            if (isSubscriber) {
                setSecondaryButton(this)
                text = resources.getString(R.string.subscribed)
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_star_filled)
            } else {
                setPrimaryButton(this)
                text = resources.getString(R.string.subscribe)
                icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_star)
            }
        }
    }

    private fun setCreator(creator: Creator) {
        if (creator.user.profileAvatarUrl.isNotEmpty()) {
            Glide.with(binding.root)
                .load(creator.user.profileAvatarUrl)
                .placeholder(R.drawable.default_profile_avatar)
                .into(binding.imageViewProfileAvatar)
        }
        if (creator.user.profileBackgroundUrl.isNotEmpty()) {
            Glide.with(binding.root)
                .load(creator.user.profileBackgroundUrl)
                .placeholder(R.drawable.default_profile_background)
                .into(binding.imageViewProfileBackground)
        }
        binding.textViewTitleWithBackButton.text = creator.user.username
        binding.textViewCreatorName.text = creator.user.username
        binding.textViewFollowerCount.text = creator.followerCount.toString()
        binding.textViewFollowers.text = resources.getQuantityString(
            R.plurals.plural_follower,
            creator.followerCount.toInt()
        )
        binding.textViewPostCount.text = creator.postCount.toString()
        binding.textViewPosts.text = resources.getQuantityString(
            R.plurals.plural_post,
            creator.postCount.toInt()
        )
    }

    private fun setUpPostRecyclerView(userId: String) {
        postAdapter = PostAdapter(
            userId = userId,
            onItemClickListener = { postItem ->
                blogViewModel.setSelectedPostId(postId = postItem.id)
                navigateToPostFragment(postId = postItem.id)
            }, onProfileAvatarClickListener = { postItem ->
                blogViewModel.setSelectedPostId(postId = postItem.id)
                navigateToPostFragment(postId = postItem.id)
            }, onMoreClickListener = { postItem ->
                blogViewModel.setSelectedPostId(postId = postItem.id)
                optionsFragment?.show(childFragmentManager, optionsFragment.toString())
            }, onSubscribeClickListener = { postItem ->
                blogViewModel.setSelectedPostId(postId = postItem.id)
                navigateToSubscriptionsFragment()
            }, onLikeClickListener = { postItem ->
                blogViewModel.insertLikeToPost(postId = postItem.id)
            }, onDislikeClickListener = { postItem ->
                blogViewModel.deleteLikeFromPost(postId = postItem.id)
            }, onCommentClickListener = { postItem ->
                blogViewModel.setSelectedPostId(postId = postItem.id)
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
                    blogViewModel.loadNextPostPage()
                }
            }
        })
    }

    private fun setPrimaryButton(button: MaterialButton) {
        button.apply {
            setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))
            backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorAccent
                )
            )
            iconTint = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    android.R.color.white
                )
            )
        }
    }

    private fun setSecondaryButton(button: MaterialButton) {
        button.apply {
            setTextColor(ContextCompat.getColor(requireContext(), R.color.textColorPrimary))
            backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorSecondary
                )
            )
            iconTint = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.iconTint
                )
            )
        }
    }

    private fun setBioFragment(creatorBioFragment: CreatorBioFragment) {
        if (!creatorBioFragment.isVisible) {
            creatorBioFragment.show(childFragmentManager, creatorBioFragment.toString())
        } else {
            creatorBioFragment.dismiss()
        }
    }

    private fun showBlogFilterFragment(blogFilterFragment: BlogFilterFragment) {
        if (!blogFilterFragment.isVisible) {
            blogFilterFragment.show(
                childFragmentManager,
                blogFilterFragment.toString()
            )
        } else {
            blogFilterFragment.dismiss()
        }
    }

    private fun navigateToGoalFragment() {
        val action = BlogFragmentDirections.actionBlogFragmentToCreditGoalsFragment(
            creatorId = arguments.creatorId
        )
        findNavController().navigate(action)
    }

    private fun navigateToTipFragment() {
        val action = BlogFragmentDirections.actionBlogFragmentToTipFragment(
            creatorId = arguments.creatorId
        )
        findNavController().navigate(action)
    }

    private fun navigateToSubscriptionsFragment() {
        val action = BlogFragmentDirections.actionBlogFragmentToSubscriptionsFragment(
            creatorId = arguments.creatorId
        )
        findNavController().navigate(action)
    }

    private fun navigateToPublishFragment() {
        val action = BlogFragmentDirections.actionBlogFragmentToCreatePostFragment(
            creatorId = arguments.creatorId
        )
        findNavController().navigate(action)
    }

    private fun navigateToPostFragment(postId: Long) {
        showToast("navigateToPostFragment (post id: $postId)")
    }

    private fun setEmptyPostRecyclerViewInfo(isPostRecyclerViewEmpty: Boolean) {
        binding.imageViewLogo.isVisible = isPostRecyclerViewEmpty
        binding.textViewEmptyListMessage.isVisible = isPostRecyclerViewEmpty
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        if (isErrorMessageShown) {
            showToast(message = errorMessage)
            blogViewModel.clearErrorMessage()
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