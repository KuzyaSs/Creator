package ru.ermakov.creator.presentation.screen.chooseCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentChooseCategoryBinding
import ru.ermakov.creator.presentation.adapter.ChooseCategoryAdapter
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class ChooseCategoryFragment : Fragment() {
    private var _binding: FragmentChooseCategoryBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var chooseCategoryViewModelFactory: ChooseCategoryViewModelFactory
    private lateinit var chooseCategoryViewModel: ChooseCategoryViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var chooseCategoryAdapter: ChooseCategoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        chooseCategoryViewModel = ViewModelProvider(
            this,
            chooseCategoryViewModelFactory
        )[ChooseCategoryViewModel::class.java]
        setUpSwipeRefreshLayout()
        setUpCategoryRecyclerView()
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

    private fun setUpCategoryRecyclerView() {
        chooseCategoryAdapter = ChooseCategoryAdapter(
            textLocalizer = textLocalizer
        ) { changedCategory ->
            chooseCategoryViewModel.updateCategoryInList(changedCategory = changedCategory)
        }
        binding.recyclerViewCategories.adapter = chooseCategoryAdapter
    }

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                chooseCategoryViewModel.refreshCategories()
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1) || recyclerViewCategories.canScrollVertically(-1)
            }
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonConfirm.setOnClickListener { confirmCategoryList() }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        chooseCategoryViewModel.chooseCategoryUiState.observe(viewLifecycleOwner) { chooseCategoryUiState ->
            chooseCategoryUiState.apply {
                if (categories != null) {
                    chooseCategoryAdapter?.submitList(categories)
                    setLoading(isLoadingShown = isProgressBarConfirmShown)
                    setErrorMessage(
                        errorMessage = errorMessage,
                        isErrorMessageShown = isErrorMessageShown
                    )
                }
                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = categories == null
                binding.progressBar.isVisible = !isErrorMessageShown && categories == null
                binding.textViewScreenErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = isErrorMessageShown && categories == null
                }
                binding.imageViewScreenLogo.isVisible = isErrorMessageShown && categories == null
            }
        }
    }

    private fun confirmCategoryList() {
        chooseCategoryViewModel.updateCategories()
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setLoading(isLoadingShown: Boolean) {
        binding.apply {
            progressBarConfirm.isVisible = isLoadingShown
            buttonConfirm.visibility = if (isLoadingShown) View.INVISIBLE else View.VISIBLE
        }
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        if (isErrorMessageShown) {
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            chooseCategoryViewModel.clearErrorMessage()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}