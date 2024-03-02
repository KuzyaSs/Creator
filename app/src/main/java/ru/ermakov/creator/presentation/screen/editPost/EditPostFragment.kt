package ru.ermakov.creator.presentation.screen.editPost

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
import ru.ermakov.creator.databinding.FragmentEditPostBinding
import ru.ermakov.creator.domain.model.PostItem
import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.domain.model.Tag
import ru.ermakov.creator.presentation.adapter.PostSubscriptionAdapter
import ru.ermakov.creator.presentation.adapter.PostTagAdapter
import ru.ermakov.creator.presentation.screen.createPost.FakeItems
import ru.ermakov.creator.presentation.screen.createPost.SelectSubscriptionsFragment
import ru.ermakov.creator.presentation.screen.createPost.SelectTagsFragment
import ru.ermakov.creator.presentation.screen.createPost.SubscriptionSelectorSource
import ru.ermakov.creator.presentation.screen.createPost.TagSelectorSource
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class EditPostFragment : Fragment(), TagSelectorSource, SubscriptionSelectorSource {
    private val arguments: EditPostFragmentArgs by navArgs()
    private var _binding: FragmentEditPostBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var editPostViewModelFactory: EditPostViewModelFactory
    private lateinit var editPostViewModel: EditPostViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var postTagAdapter: PostTagAdapter? = null
    private var postSubscriptionAdapter: PostSubscriptionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        editPostViewModel = ViewModelProvider(
            this,
            editPostViewModelFactory
        )[EditPostViewModel::class.java]
        if (editPostViewModel.editPostUiState.value?.postItem == null) {
            editPostViewModel.setEditPostScreen(
                creatorId = arguments.creatorId,
                postId = arguments.postId
            )
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
            val selectCreatePostTagsFragment = SelectTagsFragment()
            showSelectTagsFragment(selectCreatePostTagsFragment = selectCreatePostTagsFragment)
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
                editPostViewModel.refreshEditPostScreen(
                    creatorId = arguments.creatorId,
                    postId = arguments.postId
                )
            }

            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1)
            }

            textViewTitleWithBackButton.setOnClickListener { goBack() }

            buttonSaveChanges.setOnClickListener { editPost() }

            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        editPostViewModel.editPostUiState.observe(viewLifecycleOwner) { editPostUiState ->
            editPostUiState.apply {
                if (postItem != null && tags != null && subscriptions != null) {
                    setPost(
                        postItem = postItem,
                        tags = tags,
                        selectedTagIds = selectedTagIds,
                        subscriptions = subscriptions,
                        requiredSubscriptionIds = requiredSubscriptionIds
                    )
                    setLoading(isLoadingShown = isProgressBarSaveChangesShown)
                    setErrorMessage(
                        errorMessage = errorMessage,
                        isErrorMessageShown = isErrorMessageShown
                    )
                    if (isPostEdited) {
                        showToast(message = resources.getString(R.string.post_edited_successfully))
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

    private fun setPost(
        postItem: PostItem,
        tags: List<Tag>,
        selectedTagIds: List<Long>,
        subscriptions: List<Subscription>,
        requiredSubscriptionIds: List<Long>
    ) {
        if (binding.textInputEditTextTitle.text.isNullOrBlank()) {
            binding.textInputEditTextTitle.setText(postItem.title)
        }
        if (binding.textInputEditTextContent.text.isNullOrBlank()) {
            binding.textInputEditTextContent.setText(postItem.content)
        }
        postTagAdapter?.submitList(
            tags.filter { tag ->
                selectedTagIds.contains(tag.id)
            } + FakeItems.fakeTag
        )
        postSubscriptionAdapter?.submitList(
            subscriptions.filter { subscription ->
                requiredSubscriptionIds.contains(subscription.id)
            } + FakeItems.fakeSubscription
        )
    }

    private fun editPost() {
        val title = binding.textInputEditTextTitle.text?.trim().toString()
        val content = binding.textInputEditTextContent.text?.trim().toString()
        editPostViewModel.editPost(
            creatorId = arguments.creatorId,
            postId = arguments.postId,
            title = title,
            content = content
        )
    }

    private fun showSelectTagsFragment(selectCreatePostTagsFragment: SelectTagsFragment) {
        if (!selectCreatePostTagsFragment.isVisible) {
            selectCreatePostTagsFragment.show(
                childFragmentManager,
                selectCreatePostTagsFragment.toString()
            )
        } else {
            selectCreatePostTagsFragment.dismiss()
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
            progressBarSaveChanges.isVisible = isLoadingShown
            buttonSaveChanges.visibility = if (isLoadingShown) View.INVISIBLE else View.VISIBLE
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
        return editPostViewModel.editPostUiState.value?.tags
    }

    override fun getSelectedTagIds(): List<Long> {
        return editPostViewModel.editPostUiState.value?.selectedTagIds ?: listOf()
    }

    override fun changeSelectedTagIds(selectedTagIds: List<Long>) {
        editPostViewModel.changeSelectedTagIds(selectedTagIds = selectedTagIds)
    }

    override fun navigateToTagsFragment() {
        val action = EditPostFragmentDirections.actionEditPostFragmentToTagsFragment(
            creatorId = editPostViewModel.editPostUiState.value?.creatorId ?: ""
        )
        findNavController().navigate(action)
    }

    override fun getSubscriptions(): List<Subscription>? {
        return editPostViewModel.editPostUiState.value?.subscriptions
    }

    override fun getRequiredSubscriptionIds(): List<Long> {
        return editPostViewModel.editPostUiState.value?.requiredSubscriptionIds ?: listOf()
    }

    override fun changeSelectedSubscriptionIds(selectedSubscriptionIds: List<Long>) {
        editPostViewModel.changeSelectedSubscriptionIds(
            selectedSubscriptionIds = selectedSubscriptionIds
        )
    }

    override fun navigateToSubscriptionsFragment() {
        val action = EditPostFragmentDirections.actionEditPostFragmentToSubscriptionsFragment(
            creatorId = editPostViewModel.editPostUiState.value?.creatorId ?: ""
        )
        findNavController().navigate(action)
    }
}