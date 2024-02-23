package ru.ermakov.creator.presentation.screen.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentSearchBinding
import ru.ermakov.creator.presentation.adapter.ViewPagerAdapter
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.search.searchCreator.CreatorPageHandler
import ru.ermakov.creator.presentation.screen.search.searchCreator.SearchCreatorFragment
import ru.ermakov.creator.presentation.screen.search.searchCreator.SearchCreatorViewModel
import ru.ermakov.creator.presentation.screen.search.searchCreator.SearchCreatorViewModelFactory
import ru.ermakov.creator.presentation.screen.search.searchPost.PostPageHandler
import ru.ermakov.creator.presentation.screen.search.searchPost.SearchPostFragment
import ru.ermakov.creator.presentation.screen.search.searchPost.SearchPostViewModel
import ru.ermakov.creator.presentation.screen.search.searchPost.SearchPostViewModelFactory
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class SearchFragment : Fragment(), CreatorPageHandler, PostPageHandler {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var searchCreatorViewModelFactory: SearchCreatorViewModelFactory
    private lateinit var searchCreatorViewModel: SearchCreatorViewModel

    @Inject
    lateinit var searchPostViewModelFactory: SearchPostViewModelFactory
    private lateinit var searchPostViewModel: SearchPostViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreatorActivity).showBottomNavigationView()
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        searchCreatorViewModel = ViewModelProvider(
            requireActivity(),
            searchCreatorViewModelFactory
        )[SearchCreatorViewModel::class.java]
        searchPostViewModel = ViewModelProvider(
            requireActivity(),
            searchPostViewModelFactory
        )[SearchPostViewModel::class.java]
        setUpTabLayoutWithViewPager()
        setUpListeners()
        setUpObservers()
        binding.textInputEditTextSearch.setText(
            searchCreatorViewModel.searchCreatorUiState.value?.lastSearchQuery.toString()
        )
    }

    private fun setUpTabLayoutWithViewPager() {
        val fragmentList = listOf(SearchCreatorFragment(), SearchPostFragment())
        val fragmentTitleList = listOf(
            resources.getString(R.string.creators),
            resources.getString(R.string.posts)
        )

        val viewPagerAdapter = ViewPagerAdapter(
            fragment = this,
            fragmentList = fragmentList
        )
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = fragmentTitleList[position]
        }.attach()
    }

    private fun setUpListeners() {
        binding.textInputEditTextSearch.addTextChangedListener { searchQuery ->
            searchCreatorViewModel.searchCreators(searchQuery = searchQuery?.trim().toString())
            searchPostViewModel.searchPosts(searchQuery = searchQuery?.trim().toString())
        }
    }

    private fun setUpObservers() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun loadNextCreatorPage() {
        searchCreatorViewModel.loadNextCreatorPage(binding.textInputEditTextSearch.text.toString())
    }

    override fun loadNextPostPage() {
        searchPostViewModel.loadNextPostPage(binding.textInputEditTextSearch.text.toString())
    }
}