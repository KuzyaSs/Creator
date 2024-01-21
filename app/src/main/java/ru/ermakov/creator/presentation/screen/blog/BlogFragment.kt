package ru.ermakov.creator.presentation.screen.blog

import android.content.res.ColorStateList
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
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentBlogBinding
import ru.ermakov.creator.domain.model.Creator
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class BlogFragment : Fragment() {
    private val arguments: BlogFragmentArgs by navArgs()
    private var _binding: FragmentBlogBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var blogViewModelFactory: BlogViewModelFactory
    private lateinit var blogViewModel: BlogViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

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
            blogViewModel.setBlog(creatorId = arguments.creatorId)
        }
        setUpSwipeRefreshLayout()
        setUpPostRecyclerView()
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

    private fun setUpPostRecyclerView() {
        // Implement later.
    }

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                blogViewModel.refreshBlog(arguments.creatorId)
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                binding.scrollView.canScrollVertically(-1)
            }
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            val creatorBioFragment = CreatorBioFragment()
            textViewAbout.setOnClickListener { setBioFragment(creatorBioFragment = creatorBioFragment) }
            textViewGoals.setOnClickListener { navigateToGoalFragment() }
            textViewTip.setOnClickListener { navigateToTipFragment() }
            textViewSubscriptions.setOnClickListener {
                navigateToSubscriptionFragment(creatorId = arguments.creatorId)
            }
            buttonFollow.setOnClickListener {
                if (blogViewModel.blogUiState.value?.isFollower == true) {
                    blogViewModel.unfollow()
                } else {
                    blogViewModel.follow()
                }
            }
            buttonSubscribe.setOnClickListener {
                navigateToSubscriptionFragment(creatorId = arguments.creatorId)
            }
            buttonPublish.setOnClickListener { navigateToPublishFragment() }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        blogViewModel.blogUiState.observe(viewLifecycleOwner) { blogUiState ->
            blogUiState.apply {
                if (creator != null) {
                    val isOwner = currentUserId == creator.user.id
                    binding.textViewTip.isVisible = !isOwner
                    binding.textViewSubscriptions.isVisible = isOwner
                    setFollowButton(isFollower = isFollower, isOwner = isOwner)
                    setSubscribeButton(isSubscriber = isSubscriber, isOwner = isOwner)
                    binding.buttonPublish.visibility = if (isOwner) View.VISIBLE else View.INVISIBLE
                    setCreator(creator = creator)
                    setErrorMessage(
                        errorMessage = errorMessage,
                        isErrorMessageShown = isErrorMessageShown
                    )
                }
                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = creator == null
                binding.progressBar.isVisible = !isErrorMessageShown && creator == null
                binding.textViewScreenErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = isErrorMessageShown && creator == null
                }
                binding.imageViewScreenLogo.isVisible = isErrorMessageShown && creator == null
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

    private fun navigateToSubscriptionFragment(creatorId: String) {
        val action = BlogFragmentDirections.actionBlogFragmentToSubscriptionsFragment(
            creatorId = creatorId
        )
        findNavController().navigate(action)
    }

    private fun navigateToPublishFragment() {

    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        if (isErrorMessageShown) {
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            blogViewModel.clearErrorMessage()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}