package ru.ermakov.creator.presentation.screen.donateToCreditGoal

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
import okhttp3.internal.toLongOrDefault
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentDonateToCreditGoalBinding
import ru.ermakov.creator.domain.model.CreditGoal
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

private const val MAX_PROGRESS_BAR_VALUE = 100

class DonateToCreditGoalFragment : Fragment() {
    private val arguments: DonateToCreditGoalFragmentArgs by navArgs()
    private var _binding: FragmentDonateToCreditGoalBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var donateToCreditGoalViewModelFactory: DonateToCreditGoalViewModelFactory
    private lateinit var donateToCreditGoalViewModel: DonateToCreditGoalViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDonateToCreditGoalBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        donateToCreditGoalViewModel = ViewModelProvider(
            this,
            donateToCreditGoalViewModelFactory
        )[DonateToCreditGoalViewModel::class.java]
        if (donateToCreditGoalViewModel.donateToCreditGoalUiState.value?.creditGoal == null) {
            donateToCreditGoalViewModel.setDonateToCreditGoalFragment(
                creditGoalId = arguments.creditGoalId
            )
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
                donateToCreditGoalViewModel.refreshDonateToCreditGoalFragment(
                    creditGoalId = arguments.creditGoalId
                )
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1)
            }
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonDonate.setOnClickListener { donate() }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        donateToCreditGoalViewModel.donateToCreditGoalUiState.observe(viewLifecycleOwner) { donateToCreditGoalUiState ->
            donateToCreditGoalUiState.apply {
                if (creditGoal != null) {
                    setCreditGoal(creditGoal = creditGoal)
                    setBalance(balance = balance)
                    setLoading(isLoadingShown = isProgressBarDonateShown)
                    setErrorMessage(
                        errorMessage = errorMessage,
                        isErrorMessageShown = isErrorMessageShown
                    )
                    if (isDonated) {
                        showToast(message = resources.getString(R.string.donate_sent_successfully))
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
        binding.apply {
            imageViewCreditGoalCheck.isVisible = creditGoal.balance >= creditGoal.targetBalance
            textViewCreditGoalBalance.text = root.resources.getString(
                R.string.balance_of_target_balance,
                creditGoal.balance,
                creditGoal.targetBalance
            )
            progressBarCreditGoalDonate.apply {
                max = MAX_PROGRESS_BAR_VALUE
                progress =
                    (creditGoal.balance.toDouble() / creditGoal.targetBalance * MAX_PROGRESS_BAR_VALUE).toInt()
            }
            textViewCreditGoalDescription.text = creditGoal.description
        }
    }

    private fun setBalance(balance: Long) {
        binding.textViewBalance.text = getString(R.string.balance_with_number, balance)
    }

    private fun donate() {
        val creatorId = arguments.creatorId
        val creditGoalId = arguments.creditGoalId
        val amount = binding.textInputEditTextDonateAmount.text.toString().toLongOrDefault(0)
        val message = binding.textInputEditTextMessage.text?.trim().toString()
        donateToCreditGoalViewModel.donate(
            creatorId = creatorId,
            creditGoalId = creditGoalId,
            amount = amount,
            message = message
        )
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setLoading(isLoadingShown: Boolean) {
        binding.apply {
            progressBarButtonDonate.isVisible = isLoadingShown
            buttonDonate.visibility = if (isLoadingShown) View.INVISIBLE else View.VISIBLE
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