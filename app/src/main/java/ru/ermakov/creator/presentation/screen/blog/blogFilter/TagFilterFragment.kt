package ru.ermakov.creator.presentation.screen.blog.blogFilter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentTagFilterBinding
import ru.ermakov.creator.presentation.adapter.ChooseTagAdapter
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.blog.BlogViewModel
import ru.ermakov.creator.presentation.screen.blog.BlogViewModelFactory
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class TagFilterFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentTagFilterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var blogViewModelFactory: BlogViewModelFactory
    private lateinit var blogViewModel: BlogViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var chooseTagAdapter: ChooseTagAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTagFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        blogViewModel = ViewModelProvider(
            requireParentFragment(), blogViewModelFactory
        )[BlogViewModel::class.java]
        (activity as CreatorActivity).showBottomNavigationView()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.apply {
            textViewTitleWithBackButton.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun setUpObservers() {
        blogViewModel.blogUiState.observe(viewLifecycleOwner) { blogUiState ->
            blogUiState.apply {
                if (chooseTagAdapter == null) {
                    setUpTagRecyclerView(selectedTagIds = blogFilter.tagIds)
                }
                chooseTagAdapter?.submitList(blogUiState.tags)
                binding.linearLayoutRecyclerViewCategoriesState.isVisible =
                    blogUiState.tags.isNullOrEmpty()
            }
        }
    }

    private fun setUpTagRecyclerView(selectedTagIds: List<Long>) {
        chooseTagAdapter = ChooseTagAdapter(
            selectedTagIds = selectedTagIds.toMutableList(),
            onItemClickListener = {
                Log.d("MY_TAG", chooseTagAdapter?.getSelectedTagIds().toString())
                blogViewModel.changeTagFilter(
                    chooseTagAdapter?.getSelectedTagIds() ?: listOf()
                )
            }
        )
        binding.recyclerViewTags.adapter = chooseTagAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        chooseTagAdapter = null
    }
}