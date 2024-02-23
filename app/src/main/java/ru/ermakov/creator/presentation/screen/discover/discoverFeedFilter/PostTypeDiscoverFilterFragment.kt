package ru.ermakov.creator.presentation.screen.discover.discoverFeedFilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentPostTypeFilterBinding
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.discover.DiscoverViewModel
import ru.ermakov.creator.presentation.screen.discover.DiscoverViewModelFactory
import ru.ermakov.creator.presentation.screen.following.DefaultFeedFilter.ALL_POST_TYPE
import ru.ermakov.creator.presentation.screen.following.DefaultFeedFilter.AVAILABLE_POST_TYPE
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class PostTypeDiscoverFilterFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentPostTypeFilterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var discoverViewModelFactory: DiscoverViewModelFactory
    private lateinit var discoverViewModel: DiscoverViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostTypeFilterBinding.inflate(inflater, container, false)
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
    }

    private fun setUpListeners() {
        binding.apply {
            textViewTitleWithBackButton.setOnClickListener {
                dismiss()
            }

            textViewAllPosts.setOnClickListener {
                discoverViewModel.changePostTypeFilter(
                    ALL_POST_TYPE
                )
                dismiss()
            }
            textViewAvailableToMe.setOnClickListener {
                discoverViewModel.changePostTypeFilter(
                    AVAILABLE_POST_TYPE
                )
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}