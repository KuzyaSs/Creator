package ru.ermakov.creator.presentation.screen.blog.blogFilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentPostTypeFilterBinding
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.blog.BlogViewModel
import ru.ermakov.creator.presentation.screen.blog.BlogViewModelFactory
import ru.ermakov.creator.presentation.screen.following.DefaultFeedFilter.ALL_POST_TYPE
import ru.ermakov.creator.presentation.screen.following.DefaultFeedFilter.AVAILABLE_POST_TYPE
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class PostTypeBlogFilterFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentPostTypeFilterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var blogViewModelFactory: BlogViewModelFactory
    private lateinit var blogViewModel: BlogViewModel

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
        blogViewModel = ViewModelProvider(
            requireParentFragment(), blogViewModelFactory
        )[BlogViewModel::class.java]
        (activity as CreatorActivity).showBottomNavigationView()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.apply {
            textViewTitleWithBackButton.setOnClickListener {
                dismiss()
            }

            textViewAllPosts.setOnClickListener {
                blogViewModel.changePostTypeFilter(
                    ALL_POST_TYPE
                )
                dismiss()
            }
            textViewAvailableToMe.setOnClickListener {
                blogViewModel.changePostTypeFilter(
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