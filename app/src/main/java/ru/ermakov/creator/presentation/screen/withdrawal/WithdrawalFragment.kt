package ru.ermakov.creator.presentation.screen.withdrawal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import okhttp3.internal.toLongOrDefault
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentWithdrawalBinding
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class WithdrawalFragment : Fragment() {
    private var _binding: FragmentWithdrawalBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var withdrawalViewModelFactory: WithdrawalViewModelFactory
    private lateinit var withdrawalViewModel: WithdrawalViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWithdrawalBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        withdrawalViewModel =
            ViewModelProvider(this, withdrawalViewModelFactory)[WithdrawalViewModel::class.java]
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
                withdrawalViewModel.refreshWithdrawalFragment()
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1)
            }
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonWithdraw.setOnClickListener { withdraw() }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        withdrawalViewModel.withdrawalUiState.observe(viewLifecycleOwner) { withdrawalUiState ->
            withdrawalUiState.apply {
                if (balance != null) {
                    setBalance(balance = balance)
                    setLoading(isLoadingShown = isProgressBarWithdrawShown)
                    setErrorMessage(
                        errorMessage = errorMessage,
                        isErrorMessageShown = isErrorMessageShown
                    )
                    if (isWithdrawn) {
                        showToast(message = resources.getString(R.string.balance_withdrawn_successfully))
                        goBack()
                    }
                }

                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = balance == null
                binding.progressBarScreen.isVisible = isLoadingShown && balance == null
                binding.textViewScreenErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = isErrorMessageShown && balance == null
                }
                binding.imageViewScreenLogo.isVisible = isErrorMessageShown && balance == null
            }
        }
    }

    private fun setBalance(balance: Long) {
        binding.textViewBalance.text = getString(R.string.balance_with_number, balance)
    }

    private fun withdraw() {
        val amount = binding.textInputEditTextWithdrawalAmount.text.toString().toLongOrDefault(0)
        val cardNumber = binding.textInputEditTextCardNumber.text.toString()
        withdrawalViewModel.withdraw(
            amount = amount,
            cardNumber = cardNumber
        )
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setLoading(isLoadingShown: Boolean) {
        binding.apply {
            progressBarScreen.isVisible = isLoadingShown
            buttonWithdraw.visibility = if (isLoadingShown) View.INVISIBLE else View.VISIBLE
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