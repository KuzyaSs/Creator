package ru.ermakov.creator.presentation.screen.blog.blogFilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentBlogFilterBinding
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.blog.BlogViewModel
import ru.ermakov.creator.presentation.screen.blog.BlogViewModelFactory
import ru.ermakov.creator.presentation.screen.following.DefaultFeedFilter.ALL_POST_TYPE
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class BlogFilterFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBlogFilterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var blogViewModelFactory: BlogViewModelFactory
    private lateinit var blogViewModel: BlogViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlogFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        blogViewModel = ViewModelProvider(
            requireParentFragment(), blogViewModelFactory
        )[BlogViewModel::class.java]
        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.apply {
            val tagFilterFragment = TagFilterFragment()
            textViewTag.setOnClickListener {
                showTagBlogFilterFragment(tagFilterFragment = tagFilterFragment)
            }

            val postTypeBlogFilterFragment = PostTypeBlogFilterFragment()
            textViewPostType.setOnClickListener {
                showPostTypeBlogFilterFragment(postTypeBlogFilterFragment = postTypeBlogFilterFragment)
            }

            buttonReset.setOnClickListener {
                blogViewModel.resetBlogFilter()
            }

            buttonApply.setOnClickListener {
                blogViewModel.refreshBlogScreen(
                    blogViewModel.blogUiState.value?.creator?.user?.id ?: ""
                )
                dismiss()
            }
        }
    }

    private fun setUpObservers() {
        blogViewModel.blogUiState.observe(viewLifecycleOwner) { blogUiState ->
            blogUiState.apply {
                if (blogFilter.postType == ALL_POST_TYPE) {
                    binding.textViewPostType.text = resources.getString(R.string.all_posts)
                } else {
                    binding.textViewPostType.text =
                        resources.getString(R.string.available_to_me)
                }

                if (blogFilter.tagIds.isEmpty()) {
                    binding.textViewTag.text = resources.getString(R.string.any_tags)
                } else {
                    binding.textViewTag.text = resources.getString(
                        R.string.selected_number,
                        blogFilter.tagIds.size
                    )
                }
            }
        }
    }

    private fun showPostTypeBlogFilterFragment(
        postTypeBlogFilterFragment: PostTypeBlogFilterFragment
    ) {
        if (!postTypeBlogFilterFragment.isVisible) {
            postTypeBlogFilterFragment.show(
                parentFragmentManager,
                postTypeBlogFilterFragment.toString()
            )
        } else {
            postTypeBlogFilterFragment.dismiss()
        }
    }

    private fun showTagBlogFilterFragment(
        tagFilterFragment: TagFilterFragment
    ) {
        if (!tagFilterFragment.isVisible) {
            tagFilterFragment.show(
                parentFragmentManager,
                tagFilterFragment.toString()
            )
        } else {
            tagFilterFragment.dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}