package ru.ermakov.creator.presentation.screen.chooseUserCategory

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
import ru.ermakov.creator.databinding.FragmentChooseUserCategoryBinding
import ru.ermakov.creator.presentation.adapter.ChooseUserCategoryAdapter
import ru.ermakov.creator.presentation.exception.ExceptionLocalizer
import javax.inject.Inject

class ChooseUserCategoryFragment : Fragment() {
    private var _binding: FragmentChooseUserCategoryBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var chooseUserCategoryViewModelFactory: ChooseUserCategoryViewModelFactory
    private lateinit var chooseUserCategoryViewModel: ChooseUserCategoryViewModel

    @Inject
    lateinit var exceptionLocalizer: ExceptionLocalizer

    private lateinit var chooseUserCategoryAdapter: ChooseUserCategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseUserCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        chooseUserCategoryViewModel = ViewModelProvider(
            this,
            chooseUserCategoryViewModelFactory
        )[ChooseUserCategoryViewModel::class.java]
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
        chooseUserCategoryAdapter = ChooseUserCategoryAdapter { changedCategory ->
            chooseUserCategoryViewModel.updateUserCategoryInList(changedUserCategory = changedCategory)
        }
        binding.recyclerViewCategories.adapter = chooseUserCategoryAdapter
    }

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                chooseUserCategoryViewModel.refreshUserCategories()
            }
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonConfirm.setOnClickListener { confirmUserCategoryList() }
        }
    }

    private fun setUpObservers() {
        chooseUserCategoryViewModel.chooseUserCategoryUiState.observe(viewLifecycleOwner) { chooseCategoryUiState ->
            chooseCategoryUiState.apply {
                if (userCategories != null) {
                    chooseUserCategoryAdapter.submitList(userCategories)
                    setLoading(isLoadingShown = isProgressBarConfirmShown)
                    setErrorMessage(
                        errorMessage = chooseCategoryErrorMessage,
                        isErrorMessageShown = isChooseCategoryErrorMessageShown
                    )
                }
                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = userCategories == null
                binding.progressBar.isVisible =
                    !isChooseCategoryErrorMessageShown && userCategories == null
                binding.textViewErrorMessage.apply {
                    text = exceptionLocalizer.localizeException(
                        errorMessage = chooseCategoryErrorMessage
                    )
                    isVisible = isChooseCategoryErrorMessageShown && userCategories == null
                }
            }
        }
    }

    private fun confirmUserCategoryList() {
        chooseUserCategoryViewModel.updateUserCategories()
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
            chooseUserCategoryViewModel.clearChooseCategoryErrorMessage()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}