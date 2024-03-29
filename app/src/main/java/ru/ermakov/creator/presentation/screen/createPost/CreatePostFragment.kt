package ru.ermakov.creator.presentation.screen.createPost

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
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentCreatePostBinding
import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.domain.model.Tag
import ru.ermakov.creator.presentation.adapter.PostSubscriptionAdapter
import ru.ermakov.creator.presentation.adapter.PostTagAdapter
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class CreatePostFragment : Fragment(), TagSelectorSource, SubscriptionSelectorSource {
    private val arguments: CreatePostFragmentArgs by navArgs()
    private var _binding: FragmentCreatePostBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var createPostViewModelFactory: CreatePostViewModelFactory
    private lateinit var createPostViewModel: CreatePostViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var postTagAdapter: PostTagAdapter? = null
    private var postSubscriptionAdapter: PostSubscriptionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatePostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        createPostViewModel = ViewModelProvider(
            this,
            createPostViewModelFactory
        )[CreatePostViewModel::class.java]
        if (createPostViewModel.createPostUiState.value?.tags == null) {
            createPostViewModel.setCreatePostScreen(creatorId = arguments.creatorId)
        }
        setUpSwipeRefreshLayout()
        setUpSubscriptionRecyclerView()
        setUpTagRecyclerView()
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

    private fun setUpTagRecyclerView() {
        postTagAdapter = PostTagAdapter(onChangeClickListener = {
            val selectTagsFragment = SelectTagsFragment()
            showSelectTagsFragment(selectTagsFragment = selectTagsFragment)
        })
        binding.recyclerViewTags.adapter = postTagAdapter
    }

    private fun setUpSubscriptionRecyclerView() {
        postSubscriptionAdapter = PostSubscriptionAdapter(onChangeClickListener = {
            val selectSubscriptionsFragment = SelectSubscriptionsFragment()
            showSelectSubscriptionsFragment(selectSubscriptionsFragment = selectSubscriptionsFragment)
        })
        binding.recyclerViewSubscriptions.adapter = postSubscriptionAdapter
    }

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                createPostViewModel.refreshCreatePostScreen(creatorId = arguments.creatorId)
            }

            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1)
            }

            textViewTitleWithBackButton.setOnClickListener { goBack() }

            buttonPublish.setOnClickListener { publishPost() }

            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        createPostViewModel.createPostUiState.observe(viewLifecycleOwner) { createPostUiState ->
            createPostUiState.apply {
                if (tags != null && subscriptions != null) {
                    postTagAdapter?.submitList(
                        tags.filter { tag ->
                            selectedTagIds.contains(tag.id)
                        } + FakeItems.fakeTag
                    )
                    postSubscriptionAdapter?.submitList(
                        subscriptions.filter { subscription ->
                            requiredSubscriptionIds.contains(subscription.id)
                        } + FakeItems.fakeSubscription)
                    setLoading(isLoadingShown = isProgressBarPublishShown)
                    setErrorMessage(
                        errorMessage = errorMessage,
                        isErrorMessageShown = isErrorMessageShown
                    )
                    if (isPostPublished) {
                        showToast(message = resources.getString(R.string.post_published_successfully))
                        goBack()
                    }
                }

                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = tags == null
                binding.progressBarScreen.isVisible = isLoadingShown && tags == null
                binding.textViewScreenErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = isErrorMessageShown && tags == null
                }
                binding.imageViewScreenLogo.isVisible = isErrorMessageShown && tags == null
            }
        }
    }

    private fun publishPost() {
        val title = binding.textInputEditTextTitle.text?.trim().toString()
        val content = binding.textInputEditTextContent.text?.trim().toString()
        createPostViewModel.publishPost(
            creatorId = arguments.creatorId,
            title = title,
            content = content
        )
    }

    private fun showSelectTagsFragment(selectTagsFragment: SelectTagsFragment) {
        if (!selectTagsFragment.isVisible) {
            selectTagsFragment.show(
                childFragmentManager,
                selectTagsFragment.toString()
            )
        } else {
            selectTagsFragment.dismiss()
        }
    }

    private fun showSelectSubscriptionsFragment(selectSubscriptionsFragment: SelectSubscriptionsFragment) {
        if (!selectSubscriptionsFragment.isVisible) {
            selectSubscriptionsFragment.show(
                childFragmentManager,
                selectSubscriptionsFragment.toString()
            )
        } else {
            selectSubscriptionsFragment.dismiss()
        }
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setLoading(isLoadingShown: Boolean) {
        binding.apply {
            progressBarPublish.isVisible = isLoadingShown
            buttonPublish.visibility = if (isLoadingShown) View.INVISIBLE else View.VISIBLE
        }
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        binding.textViewErrorMessage.apply {
            text = textLocalizer.localizeText(text = errorMessage)
            isVisible = isErrorMessageShown
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getTags(): List<Tag>? {
        return createPostViewModel.createPostUiState.value?.tags
    }

    override fun getSelectedTagIds(): List<Long> {
        return createPostViewModel.createPostUiState.value?.selectedTagIds ?: listOf()
    }

    override fun changeSelectedTagIds(selectedTagIds: List<Long>) {
        createPostViewModel.changeSelectedTagIds(selectedTagIds = selectedTagIds)
    }

    override fun navigateToTagsFragment() {
        val action = CreatePostFragmentDirections.actionCreatePostFragmentToTagsFragment(
            creatorId = createPostViewModel.createPostUiState.value?.creatorId ?: ""
        )
        findNavController().navigate(action)
    }

    override fun getSubscriptions(): List<Subscription>? {
        return createPostViewModel.createPostUiState.value?.subscriptions
    }

    override fun getRequiredSubscriptionIds(): List<Long> {
        return createPostViewModel.createPostUiState.value?.requiredSubscriptionIds ?: listOf()
    }

    override fun changeSelectedSubscriptionIds(selectedSubscriptionIds: List<Long>) {
        createPostViewModel.changeSelectedSubscriptionIds(
            selectedSubscriptionIds = selectedSubscriptionIds
        )
    }

    override fun navigateToSubscriptionsFragment() {
        val action = CreatePostFragmentDirections.actionCreatePostFragmentToSubscriptionsFragment(
            creatorId = createPostViewModel.createPostUiState.value?.creatorId ?: ""
        )
        findNavController().navigate(action)
    }
}