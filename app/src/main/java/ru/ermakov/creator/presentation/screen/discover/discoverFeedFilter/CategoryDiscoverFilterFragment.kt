package ru.ermakov.creator.presentation.screen.discover.discoverFeedFilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentCategoryFilterBinding
import ru.ermakov.creator.presentation.adapter.ChooseCategoryAdapter
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.discover.DiscoverViewModel
import ru.ermakov.creator.presentation.screen.discover.DiscoverViewModelFactory
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class CategoryDiscoverFilterFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCategoryFilterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var discoverViewModelFactory: DiscoverViewModelFactory
    private lateinit var discoverViewModel: DiscoverViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var chooseCategoryAdapter: ChooseCategoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        discoverViewModel = ViewModelProvider(
            requireActivity(), discoverViewModelFactory
        )[DiscoverViewModel::class.java]
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
        discoverViewModel.discoverUiState.observe(viewLifecycleOwner) { discoverUiState ->
            discoverUiState.apply {
                if (chooseCategoryAdapter == null) {
                    setUpCategoryRecyclerView()
                }
                chooseCategoryAdapter?.submitList(discoverUiState.categories)
                binding.linearLayoutRecyclerViewCategoriesState.isVisible =
                    discoverUiState.categories.isNullOrEmpty()
            }
        }
    }

    private fun setUpCategoryRecyclerView() {
        chooseCategoryAdapter = ChooseCategoryAdapter(
            textLocalizer = textLocalizer,
            onItemClickListener = { category ->
                discoverViewModel.changeCategoryFilter(changedCategory = category)
            }
        )
        binding.recyclerViewCategories.adapter = chooseCategoryAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        chooseCategoryAdapter = null
    }
}