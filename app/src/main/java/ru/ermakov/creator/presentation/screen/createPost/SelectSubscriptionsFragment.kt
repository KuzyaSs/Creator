package ru.ermakov.creator.presentation.screen.createPost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentSelectSubscriptionsBinding
import ru.ermakov.creator.presentation.adapter.ChooseSubscriptionAdapter
import javax.inject.Inject

class SelectSubscriptionsFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSelectSubscriptionsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var createPostViewModelFactory: CreatePostViewModelFactory
    private lateinit var createPostViewModel: CreatePostViewModel

    private var chooseSubscriptionAdapter: ChooseSubscriptionAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectSubscriptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        createPostViewModel = ViewModelProvider(
            requireParentFragment(),
            createPostViewModelFactory
        )[CreatePostViewModel::class.java]
        setUpSubscriptionRecyclerView(
            selectedSubscriptionIds = createPostViewModel
                .createPostUiState
                .value
                ?.requiredSubscriptionIds
                ?.toMutableList() ?: mutableListOf(),
        )
        setUpListeners()
        setUpObservers()
    }

    private fun setUpSubscriptionRecyclerView(selectedSubscriptionIds: List<Long>) {
        chooseSubscriptionAdapter = ChooseSubscriptionAdapter(
            selectedSubscriptionIds = selectedSubscriptionIds.toMutableList(),
            onItemClickListener = {
                createPostViewModel.changeSelectedSubscriptionIds(
                    chooseSubscriptionAdapter?.getSelectedSubscriptionIds() ?: listOf()
                )
            }
        )
        binding.recyclerViewCategories.adapter = chooseSubscriptionAdapter
    }

    private fun setUpListeners() {
        binding.apply {
            textViewTitleWithBackButton.setOnClickListener {
                dismiss()
            }
            buttonManage.setOnClickListener { navigateToSubscriptionsFragment() }
        }
    }

    private fun setUpObservers() {
        createPostViewModel.createPostUiState.observe(viewLifecycleOwner) { createPostUiState ->
            createPostUiState.apply {
                chooseSubscriptionAdapter?.submitList(subscriptions)
                binding.linearLayoutRecyclerViewCategoriesState.isVisible = subscriptions.isNullOrEmpty()
            }
        }
    }

    private fun navigateToSubscriptionsFragment() {
        val action = CreatePostFragmentDirections.actionCreatePostFragmentToSubscriptionsFragment(
            creatorId = createPostViewModel.createPostUiState.value?.creatorId ?: ""
        )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}