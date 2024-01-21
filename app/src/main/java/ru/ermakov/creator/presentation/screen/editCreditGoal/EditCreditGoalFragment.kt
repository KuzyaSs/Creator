package ru.ermakov.creator.presentation.screen.editCreditGoal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentEditCreditGoalBinding
import ru.ermakov.creator.domain.model.CreditGoal
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class EditCreditGoalFragment : Fragment() {
    private val arguments: EditCreditGoalFragmentArgs by navArgs()
    private var _binding: FragmentEditCreditGoalBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var editCreditGoalViewModelFactory: EditCreditGoalViewModelFactory
    private lateinit var editCreditGoalViewModel: EditCreditGoalViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditCreditGoalBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        editCreditGoalViewModel = ViewModelProvider(
            this,
            editCreditGoalViewModelFactory
        )[EditCreditGoalViewModel::class.java]
        if (editCreditGoalViewModel.editCreditGoalUiState.value?.creditGoal == null) {
            editCreditGoalViewModel.setCreditGoal(creditGoalId = arguments.creditGoalId)
        }
        setUpSwipeRefreshLayout()
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

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                editCreditGoalViewModel.refreshCreditGoal(creditGoalId = arguments.creditGoalId)
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1)
            }
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonSaveChanges.setOnClickListener { editCreditGoal() }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        editCreditGoalViewModel.editCreditGoalUiState.observe(viewLifecycleOwner) { editCreditGoalUiState ->
            editCreditGoalUiState.apply {
                if (creditGoal != null) {
                    setCreditGoal(creditGoal = creditGoal)
                    setLoading(isLoadingShown = isProgressBarSaveChangesShown)
                    setErrorMessage(
                        errorMessage = errorMessage,
                        isErrorMessageShown = isErrorMessageShown
                    )
                    if (isCreditGoalEdited) {
                        showToast(message = resources.getString(R.string.credit_goal_edited_successfully))
                        goBack()
                    }
                }

                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = creditGoal == null
                binding.progressBarScreen.isVisible = isLoadingShown && creditGoal == null
                binding.textViewScreenErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = isErrorMessageShown && creditGoal == null
                }
                binding.imageViewScreenLogo.isVisible = isErrorMessageShown && creditGoal == null
            }
        }
    }

    private fun setCreditGoal(creditGoal: CreditGoal) {
        if (binding.textInputEditTextTargetBalance.text.isNullOrBlank()) {
            binding.textInputEditTextTargetBalance.setText(creditGoal.targetBalance.toString())
        }
        if (binding.textInputEditTextDescription.text.isNullOrBlank()) {
            binding.textInputEditTextDescription.setText(creditGoal.description)
        }
    }

    private fun editCreditGoal() {
        val targetBalance = binding.textInputEditTextTargetBalance.text.toString()
            .toLongOrNull() ?: 0
        val description = binding.textInputEditTextDescription.text?.trim().toString()
        editCreditGoalViewModel.editSubscription(
            creditGoalId = arguments.creditGoalId,
            targetBalance = targetBalance,
            description = description
        )
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setLoading(isLoadingShown: Boolean) {
        binding.apply {
            progressBarSaveChanges.isVisible = isLoadingShown
            buttonSaveChanges.visibility = if (isLoadingShown) View.INVISIBLE else View.VISIBLE
        }
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        binding.textViewErrorMessage.apply {
            text = textLocalizer.localizeText(text = errorMessage)
            isVisible = isErrorMessageShown
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