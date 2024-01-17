package ru.ermakov.creator.presentation.screen.topUp

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
import ru.ermakov.creator.databinding.FragmentTopUpBinding
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class TopUpFragment : Fragment() {
    private var _binding: FragmentTopUpBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var topUpViewModelFactory: TopUpViewModelFactory
    private lateinit var topUpViewModel: TopUpViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        topUpViewModel = ViewModelProvider(this, topUpViewModelFactory)[TopUpViewModel::class.java]
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
                topUpViewModel.refreshTopUpFragment()
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1)
            }
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonTopUp.setOnClickListener { topUp() }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        topUpViewModel.topUpUiState.observe(viewLifecycleOwner) { topUpUiState ->
            topUpUiState.apply {
                if (balance != null) {
                    setBalance(balance = balance)
                    setLoading(isLoadingShown = isProgressBarTopUpShown)
                    setErrorMessage(
                        errorMessage = errorMessage,
                        isErrorMessageShown = isErrorMessageShown
                    )
                    if (isToppedUp) {
                        showToast(message = resources.getString(R.string.balance_topped_up_successfully))
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

    private fun topUp() {
        val amount = binding.textInputEditTextTopUpAmount.text.toString().toLongOrDefault(0)
        topUpViewModel.topUp(amount = amount)
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setLoading(isLoadingShown: Boolean) {
        binding.apply {
            progressBarTopUp.isVisible = isLoadingShown
            buttonTopUp.visibility = if (isLoadingShown) View.INVISIBLE else View.VISIBLE
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