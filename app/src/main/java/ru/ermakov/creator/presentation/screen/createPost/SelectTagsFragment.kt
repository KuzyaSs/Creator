package ru.ermakov.creator.presentation.screen.createPost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentSelectTagsBinding
import ru.ermakov.creator.presentation.adapter.ChooseTagAdapter
import javax.inject.Inject

class SelectTagsFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSelectTagsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var createPostViewModelFactory: CreatePostViewModelFactory
    private lateinit var createPostViewModel: CreatePostViewModel

    private var chooseTagAdapter: ChooseTagAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectTagsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        createPostViewModel = ViewModelProvider(
            requireParentFragment(),
            createPostViewModelFactory
        )[CreatePostViewModel::class.java]
        setUpTagRecyclerView(
            selectedTagIds = createPostViewModel
                .createPostUiState
                .value
                ?.selectedTagIds
                ?.toMutableList() ?: mutableListOf(),
        )
        setUpListeners()
        setUpObservers()
    }

    private fun setUpTagRecyclerView(selectedTagIds: List<Long>) {
        chooseTagAdapter = ChooseTagAdapter(
            selectedTagIds = selectedTagIds.toMutableList(),
            onItemClickListener = {
                createPostViewModel.changeSelectedTagIds(
                    chooseTagAdapter?.getSelectedTagIds() ?: listOf()
                )
            }
        )
        binding.recyclerViewTags.adapter = chooseTagAdapter
    }

    private fun setUpListeners() {
        binding.apply {
            textViewTitleWithBackButton.setOnClickListener {
                dismiss()
            }
            buttonManage.setOnClickListener { navigateToTagsFragment() }
        }
    }

    private fun setUpObservers() {
        createPostViewModel.createPostUiState.observe(viewLifecycleOwner) { createPostUiState ->
            createPostUiState.apply {
                chooseTagAdapter?.submitList(tags)
                binding.linearLayoutRecyclerViewTagsState.isVisible = tags.isNullOrEmpty()
            }
        }
    }

    private fun navigateToTagsFragment() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}