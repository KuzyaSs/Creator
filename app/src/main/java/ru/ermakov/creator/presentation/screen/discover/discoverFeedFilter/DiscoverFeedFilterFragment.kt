package ru.ermakov.creator.presentation.screen.discover.discoverFeedFilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentFeedFilterBinding
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.discover.DiscoverViewModel
import ru.ermakov.creator.presentation.screen.discover.DiscoverViewModelFactory
import ru.ermakov.creator.presentation.screen.following.DefaultFeedFilter.ALL_POST_TYPE
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class DiscoverFeedFilterFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentFeedFilterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var discoverViewModelFactory: DiscoverViewModelFactory
    private lateinit var discoverViewModel: DiscoverViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedFilterBinding.inflate(inflater, container, false)
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
            val postTypeDiscoverFilterFragment = PostTypeDiscoverFilterFragment()
            textViewPostType.setOnClickListener {
                showPostTypeDiscoverFilterFragment(postTypeDiscoverFilterFragment = postTypeDiscoverFilterFragment)
            }

            val categoryDiscoverFilterFragment = CategoryDiscoverFilterFragment()
            textViewCategory.setOnClickListener {
                showCategoryDiscoverFilterFragment(categoryDiscoverFilterFragment = categoryDiscoverFilterFragment)
            }

            buttonReset.setOnClickListener {
                discoverViewModel.resetFeedFilter()
            }

            buttonApply.setOnClickListener {
                discoverViewModel.refreshDiscoverScreen()
                dismiss()
            }
        }
    }

    private fun setUpObservers() {
        discoverViewModel.discoverUiState.observe(viewLifecycleOwner) { discoverUiState ->
            discoverUiState.apply {
                if (feedFilter.postType == ALL_POST_TYPE) {
                    binding.textViewPostType.text = resources.getString(R.string.all_posts)
                } else {
                    binding.textViewPostType.text =
                        resources.getString(R.string.available_to_me)
                }

                if (feedFilter.categoryIds.isEmpty()) {
                    binding.textViewCategory.text = resources.getString(R.string.any_categories)
                } else {
                    binding.textViewCategory.text = resources.getString(
                        R.string.selected_number,
                        feedFilter.categoryIds.size
                    )
                }
            }
        }
    }

    private fun showPostTypeDiscoverFilterFragment(
        postTypeDiscoverFilterFragment: PostTypeDiscoverFilterFragment
    ) {
        if (!postTypeDiscoverFilterFragment.isVisible) {
            postTypeDiscoverFilterFragment.show(
                childFragmentManager,
                postTypeDiscoverFilterFragment.toString()
            )
        } else {
            postTypeDiscoverFilterFragment.dismiss()
        }
    }

    private fun showCategoryDiscoverFilterFragment(
        categoryDiscoverFilterFragment: CategoryDiscoverFilterFragment
    ) {
        if (!categoryDiscoverFilterFragment.isVisible) {
            categoryDiscoverFilterFragment.show(
                childFragmentManager,
                categoryDiscoverFilterFragment.toString()
            )
        } else {
            categoryDiscoverFilterFragment.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}