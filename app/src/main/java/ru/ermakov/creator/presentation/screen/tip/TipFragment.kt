package ru.ermakov.creator.presentation.screen.tip

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
import ru.ermakov.creator.databinding.FragmentTipBinding
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class TipFragment : Fragment() {
    private val arguments: TipFragmentArgs by navArgs()
    private var _binding: FragmentTipBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var tipViewModelFactory: TipViewModelFactory
    private lateinit var tipViewModel: TipViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTipBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        tipViewModel = ViewModelProvider(this, tipViewModelFactory)[TipViewModel::class.java]
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
                tipViewModel.refreshTipFragment()
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1)
            }
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonSendTip.setOnClickListener { sendTip() }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        tipViewModel.tipUiState.observe(viewLifecycleOwner) { tipUiState ->
            tipUiState.apply {
                if (balance != null) {
                    setBalance(balance = balance)
                    setLoading(isLoadingShown = isProgressBarSendTipShown)
                    setErrorMessage(
                        errorMessage = errorMessage,
                        isErrorMessageShown = isErrorMessageShown
                    )
                    if (isTipSent) {
                        showToast(message = resources.getString(R.string.tip_sent_successfully))
                        goBack()
                    }
                }

                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = balance == null
                binding.progressBarScreen.isVisible = isLoading && balance == null
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

    private fun sendTip() {
        val creatorId = arguments.creatorId
        val amount = binding.textInputEditTextTipAmount.text.toString().toLongOrDefault(0)
        val message = binding.textInputEditTextMessage.text.toString()
        tipViewModel.sendTip(creatorId = creatorId, amount = amount, message = message)
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setLoading(isLoadingShown: Boolean) {
        binding.apply {
            progressBarSendTip.isVisible = isLoadingShown
            buttonSendTip.visibility = if (isLoadingShown) View.INVISIBLE else View.VISIBLE
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