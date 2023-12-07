package ru.ermakov.creator.presentation.screen.search.searchCreator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentSearchCretorBinding
import ru.ermakov.creator.presentation.adapter.CreatorAdapter
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.search.SearchFragmentDirections
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

private const val THRESHOLD = 5

class SearchCreatorFragment : Fragment() {
    private var _binding: FragmentSearchCretorBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var searchCreatorViewModelFactory: SearchCreatorViewModelFactory
    private lateinit var searchCreatorViewModel: SearchCreatorViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var creatorAdapter: CreatorAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchCretorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreatorActivity).showBottomNavigationView()
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        searchCreatorViewModel = ViewModelProvider(
            requireParentFragment(),
            searchCreatorViewModelFactory
        )[SearchCreatorViewModel::class.java]
        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        setUpCreatorRecyclerView()
    }

    private fun setUpCreatorRecyclerView() {
        creatorAdapter = CreatorAdapter { creator ->
            navigateToBlogFragment(creatorId = creator.user.id)
        }
        binding.recyclerViewCreators.adapter = creatorAdapter
        binding.recyclerViewCreators.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (layoutManager.itemCount - lastVisibleItemPosition <= THRESHOLD && recyclerView.scrollState != SCROLL_STATE_IDLE) {
                    (parentFragment as CreatorLoader).loadNextCreatorPage()
                }
            }
        })
    }

    private fun setUpObservers() {
        searchCreatorViewModel.searchCreatorUiState.observe(viewLifecycleOwner) { searchCreatorUiState ->
            searchCreatorUiState.apply {
                creatorAdapter?.submitList(creators)
                setNoResultsFound(
                    isNoResultsFoundShown = creators.isNullOrEmpty() && lastSearchQuery.isNotBlank() && !isLoadingCreators
                )
                binding.progressBar.isVisible = isLoadingCreators && creators.isNullOrEmpty()
                setErrorMessage(
                    errorMessage = textLocalizer.localizeText(
                        text = errorMessage
                    ),
                    isErrorMessageShown = isErrorMessageShown
                )
            }
        }
    }

    private fun navigateToBlogFragment(creatorId: String) {
        val action = SearchFragmentDirections.actionSearchFragmentToBlogFragment(
            creatorId = creatorId
        )
        findNavController().navigate(action)
    }

    private fun setNoResultsFound(isNoResultsFoundShown: Boolean) {
        binding.imageViewLogo.isVisible = isNoResultsFoundShown
        binding.textViewNoResultsFound.isVisible = isNoResultsFoundShown
        binding.textViewNoResultsFoundDescription.isVisible = isNoResultsFoundShown
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        if (isErrorMessageShown) {
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            searchCreatorViewModel.clearErrorMessage()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}