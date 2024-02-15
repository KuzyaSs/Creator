package ru.ermakov.creator.presentation.screen.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import layout.PostTypeFilterFragment
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentFeedFilterBinding
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.following.DefaultFeedFilter.ALL_POST_TYPE
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class FeedFilterFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentFeedFilterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var followingViewModelFactory: FollowingViewModelFactory
    private lateinit var followingViewModel: FollowingViewModel

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
        followingViewModel = ViewModelProvider(
            requireActivity(), followingViewModelFactory
        )[FollowingViewModel::class.java]
        (activity as CreatorActivity).showBottomNavigationView()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.apply {
            val postTypeFilterFragment = PostTypeFilterFragment()
            textViewPostType.setOnClickListener {
                showPostTypeFilterFragment(postTypeFilterFragment = postTypeFilterFragment)
            }

            textViewCategory.setOnClickListener {

            }

            buttonReset.setOnClickListener {
                followingViewModel.resetFeedFilter()
            }

            buttonApply.setOnClickListener {
                followingViewModel.refreshFollowingScreen()
                dismiss()
            }
        }
    }

    private fun setUpObservers() {
        followingViewModel.followingUiState.observe(viewLifecycleOwner) { followingUiState ->
            followingUiState.apply {
                if (feedFilter.postType == ALL_POST_TYPE) {
                    binding.textViewPostType.text = resources.getString(R.string.all_posts)
                } else {
                    binding.textViewPostType.text =
                        resources.getString(R.string.available_to_me)
                }
            }
        }
    }

    private fun showPostTypeFilterFragment(postTypeFilterFragment: PostTypeFilterFragment) {
        if (!postTypeFilterFragment.isVisible) {
            postTypeFilterFragment.show(childFragmentManager, postTypeFilterFragment.toString())
        } else {
            postTypeFilterFragment.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}