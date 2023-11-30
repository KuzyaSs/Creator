package ru.ermakov.creator.presentation.screen.blog

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentBlogBinding
import ru.ermakov.creator.domain.model.Creator
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.verificationEmail.VerificationEmailFragmentArgs
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
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        blogViewModel.blogUiState.observe(viewLifecycleOwner) { blogUiState ->
            blogUiState.apply {
                if (creator != null) {
                    setFollowButton(isFollower = isFollower)
                    setSubscribeButton(isSubscriber = isSubscriber)
                    setCreator(creator = creator)
                    setErrorMessage(
                        errorMessage = errorMessage,
                        isErrorMessageShown = isErrorMessageShown
                    )
                }
                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = creator == null
                binding.progressBar.isVisible = !isErrorMessageShown && creator == null
                binding.textViewErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = isErrorMessageShown && creator == null
                }
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
        binding.textViewNumFollowers.text = creator.numFollowers.toString()
        binding.textViewFollowers.text = resources.getQuantityString(
            R.plurals.plural_follower,
            creator.numFollowers.toInt(),
        )
        binding.textViewNumPosts.text = creator.numPosts.toString()
        binding.textViewPosts.text = resources.getQuantityString(
            R.plurals.plural_post,
            creator.numPosts.toInt(),
        )
    }

    private fun setFollowButton(isFollower: Boolean) {
        binding.buttonFollow.apply {
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

    private fun setSubscribeButton(isSubscriber: Boolean) {
        binding.buttonSubscribe.apply {
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