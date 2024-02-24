package ru.ermakov.creator.presentation.screen.following.followingFeedFilter

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
import ru.ermakov.creator.presentation.screen.following.FollowingViewModel
import ru.ermakov.creator.presentation.screen.following.FollowingViewModelFactory
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class CategoryFollowingFilterFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCategoryFilterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var followingViewModelFactory: FollowingViewModelFactory
    private lateinit var followingViewModel: FollowingViewModel

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
        followingViewModel = ViewModelProvider(
            requireActivity(), followingViewModelFactory
        )[FollowingViewModel::class.java]
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
        followingViewModel.followingUiState.observe(viewLifecycleOwner) { followingUiState ->
            followingUiState.apply {
                if (chooseCategoryAdapter == null) {
                    setUpCategoryRecyclerView()
                }
                chooseCategoryAdapter?.submitList(followingUiState.categories)
                binding.linearLayoutRecyclerViewCategoriesState.isVisible =
                    followingUiState.categories.isNullOrEmpty()
            }
        }
    }

    private fun setUpCategoryRecyclerView() {
        chooseCategoryAdapter = ChooseCategoryAdapter(
            textLocalizer = textLocalizer,
            onItemClickListener = { category ->
                followingViewModel.changeCategoryFilter(changedCategory = category)
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