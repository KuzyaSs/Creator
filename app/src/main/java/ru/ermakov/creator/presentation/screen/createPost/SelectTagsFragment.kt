package ru.ermakov.creator.presentation.screen.createPost

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.ermakov.creator.databinding.FragmentSelectTagsBinding
import ru.ermakov.creator.presentation.adapter.ChooseTagAdapter

class SelectTagsFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentSelectTagsBinding? = null
    private val binding get() = _binding!!

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
        setUpSelectTagsScreen()
        setUpListeners()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        setUpSelectTagsScreen()
    }

    private fun setUpSelectTagsScreen() {
        val tags = (requireParentFragment() as TagSelectorSource).getTags()
        val selectedTagIds = (requireParentFragment() as TagSelectorSource).getSelectedTagIds()
        setUpTagRecyclerView(selectedTagIds = selectedTagIds)
        chooseTagAdapter?.submitList(tags)
        binding.linearLayoutRecyclerViewTagsState.isVisible = tags.isNullOrEmpty()
    }

    private fun setUpTagRecyclerView(selectedTagIds: List<Long>) {
        chooseTagAdapter = ChooseTagAdapter(
            selectedTagIds = selectedTagIds.toMutableList(),
            onItemClickListener = {
                (requireParentFragment() as TagSelectorSource).changeSelectedTagIds(
                    selectedTagIds = chooseTagAdapter?.getSelectedTagIds() ?: listOf()
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
            buttonManage.setOnClickListener {
                (requireParentFragment() as TagSelectorSource).navigateToTagsFragment()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}