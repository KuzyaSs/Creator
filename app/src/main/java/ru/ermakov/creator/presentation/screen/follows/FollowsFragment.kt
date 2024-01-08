package ru.ermakov.creator.presentation.screen.follows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentFollowsBinding
import ru.ermakov.creator.presentation.adapter.FollowAdapter
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

private const val THRESHOLD = 5

class FollowsFragment : Fragment() {
    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var followsViewModelFactory: FollowsViewModelFactory
    private lateinit var followsViewModel: FollowsViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var followsAdapter: FollowAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreatorActivity).hideBottomNavigationView()
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        followsViewModel = ViewModelProvider(
            this,
            followsViewModelFactory
        )[FollowsViewModel::class.java]
        setUpSwipeRefreshLayout()
        setUpCreatorRecyclerView()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpSwipeRefreshLayout() {
        binding.swipeRefreshLayout.apply {
            setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.backgroundColor
                )
            )
            setColorSchemeColors(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorAccent
                )
            )
        }
    }

    private fun setUpCreatorRecyclerView() {
        followsAdapter = FollowAdapter { follow ->
            navigateToBlogFragment(creatorId = follow.creator.user.id)
        }
        binding.recyclerViewFollows.adapter = followsAdapter
        binding.recyclerViewFollows.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (layoutManager.itemCount - lastVisibleItemPosition <= THRESHOLD && recyclerView.scrollState != SCROLL_STATE_IDLE) {
                    followsViewModel.loadNextFollowPage(binding.textInputEditTextSearch.text.toString())
                }
            }
        })
    }

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                followsViewModel.refreshFollows()
            }
            imageViewBack.setOnClickListener {
                goBack()
            }
            imageViewSearch.setOnClickListener {
                followsViewModel.setSearchBar(isSearchBarShown = true)
            }
            textInputEditTextSearch.addTextChangedListener { searchQuery ->
                followsViewModel.searchFollows(searchQuery = searchQuery.toString().trim())
            }
        }
    }

    private fun setUpObservers() {
        followsViewModel.followsUiState.observe(viewLifecycleOwner) { followsUiState ->
            followsUiState.apply {
                if (follows != null) {
                    followsAdapter?.submitList(follows)
                    setRecyclerViewState(
                        isFollowsEmpty = follows.isEmpty(),
                        isSearchQueryEmpty = lastSearchQuery.isBlank(),
                        isLoadingFollows = isLoadingFollows
                    )
                    setSearchBar(isSearchBarShown = isSearchBarShown)
                    setErrorMessage(
                        errorMessage = textLocalizer.localizeText(text = errorMessage),
                        isErrorMessageShown = isErrorMessageShown
                    )
                }

                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = follows == null
                binding.progressBarScreen.isVisible = isLoadingFollows && follows == null
                binding.textViewScreenErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = isErrorMessageShown && follows == null
                }
                binding.imageViewScreenLogo.isVisible = isErrorMessageShown && follows == null
            }
        }
    }

    private fun navigateToBlogFragment(creatorId: String) {
        val action = FollowsFragmentDirections.actionFollowsFragmentToBlogFragment(
            creatorId = creatorId
        )
        findNavController().navigate(action)
    }

    private fun setRecyclerViewState(
        isFollowsEmpty: Boolean,
        isSearchQueryEmpty: Boolean,
        isLoadingFollows: Boolean
    ) {
        if (isFollowsEmpty && !isLoadingFollows) {
            if (isSearchQueryEmpty) {
                binding.textViewFollowsTitle.text = "EMPTY"
                binding.textViewFollowsDescription.text = "EMPTY DESCRIPTION"
            } else {
                binding.textViewFollowsTitle.text = "NO RESULTS FOUND"
                binding.textViewFollowsDescription.text = "EMPTY DESCRIPTION"
            }
        }
        binding.imageViewFollowsLogo.isVisible = isFollowsEmpty && !isLoadingFollows
        binding.textViewFollowsTitle.isVisible = isFollowsEmpty && !isLoadingFollows
        binding.textViewFollowsDescription.isVisible = isFollowsEmpty && !isLoadingFollows
        binding.progressBarFollows.isVisible = isFollowsEmpty && isLoadingFollows
    }

    private fun setSearchBar(isSearchBarShown: Boolean) {
        binding.apply {
            textInputLayoutSearch.isVisible = isSearchBarShown
            textViewTitle.isVisible = !isSearchBarShown
            imageViewSearch.isVisible = !isSearchBarShown
        }
    }

    private fun goBack() {
        if (followsViewModel.followsUiState.value?.isSearchBarShown == true) {
            followsViewModel.setSearchBar(isSearchBarShown = false)
        } else {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        if (isErrorMessageShown) {
            showToast(message = errorMessage)
            followsViewModel.clearErrorMessage()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}