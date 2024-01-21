package ru.ermakov.creator.presentation.screen.createCreditGoal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentCreateCreditGoalBinding
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class CreateCreditGoalFragment : Fragment() {
    private var _binding: FragmentCreateCreditGoalBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var createCreditGoalViewModelFactory: CreateCreditGoalViewModelFactory
    private lateinit var createCreditGoalViewModel: CreateCreditGoalViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateCreditGoalBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        createCreditGoalViewModel = ViewModelProvider(
            this,
            createCreditGoalViewModelFactory
        )[CreateCreditGoalViewModel::class.java]
        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.apply {
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonCreate.setOnClickListener { createCreditGoal() }
        }
    }

    private fun setUpObservers() {
        createCreditGoalViewModel.createCreditGoalUiState.observe(viewLifecycleOwner) { createCreditGoalUiState ->
            createCreditGoalUiState.apply {
                setLoading(isLoadingShown = isProgressBarCreateShown)
                setErrorMessage(
                    errorMessage = errorMessage,
                    isErrorMessageShown = isErrorMessageShown
                )
                if (isCreditGoalCreated) {
                    showToast(message = resources.getString(R.string.credit_goal_created_successfully))
                    goBack()
                }
            }
        }
    }

    private fun createCreditGoal() {
        val targetBalance =
            binding.textInputEditTextTargetBalance.text.toString().toLongOrNull() ?: 0
        val description = binding.textInputEditTextDescription.text?.trim().toString()
        createCreditGoalViewModel.createCreditGoal(
            targetBalance = targetBalance,
            description = description
        )
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setLoading(isLoadingShown: Boolean) {
        binding.apply {
            progressBarCreate.isVisible = isLoadingShown
            buttonCreate.visibility = if (isLoadingShown) View.INVISIBLE else View.VISIBLE
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