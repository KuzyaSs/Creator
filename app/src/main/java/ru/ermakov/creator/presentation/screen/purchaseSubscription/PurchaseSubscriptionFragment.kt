package ru.ermakov.creator.presentation.screen.purchaseSubscription

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
import androidx.recyclerview.widget.LinearLayoutManager
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentPurchaseSubscriptionBinding
import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.presentation.adapter.SubscriptionPeriodAdapter
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class PurchaseSubscriptionFragment : Fragment() {
    private val arguments: PurchaseSubscriptionFragmentArgs by navArgs()
    private var _binding: FragmentPurchaseSubscriptionBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var purchaseSubscriptionViewModelFactory: PurchaseSubscriptionViewModelFactory
    private lateinit var purchaseSubscriptionViewModel: PurchaseSubscriptionViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var subscriptionPeriodAdapter: SubscriptionPeriodAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPurchaseSubscriptionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        purchaseSubscriptionViewModel = ViewModelProvider(
            this,
            purchaseSubscriptionViewModelFactory
        )[PurchaseSubscriptionViewModel::class.java]
        if (purchaseSubscriptionViewModel.purchaseSubscriptionUiState.value?.subscription == null) {
            purchaseSubscriptionViewModel.setPurchaseSubscription(subscriptionId = arguments.subscriptionId)
        }
        setUpSwipeRefreshLayout()
        setUpSubscriptionPeriodRecyclerView(
            purchaseSubscriptionViewModel.purchaseSubscriptionUiState.value?.selectedSubscriptionPeriod
                ?: DEFAULT_SUBSCRIPTION_PERIOD
        )
        setUpListeners()
        setUpObservers()
    }

    private fun setUpSubscriptionPeriodRecyclerView(selectedSubscriptionPeriod: Int) {
        subscriptionPeriodAdapter = SubscriptionPeriodAdapter(
            selectedSubscriptionPeriod = selectedSubscriptionPeriod
        ) { durationInMonths ->
            purchaseSubscriptionViewModel.setSelectedSubscriptionPeriod(durationInMonths = durationInMonths)
        }
        binding.recyclerViewSubscriptionPeriods.adapter = subscriptionPeriodAdapter
        binding.recyclerViewSubscriptionPeriods.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        // Duration in months.
        val subscriptionPeriods = listOf(1, 3, 6, 12)
        subscriptionPeriodAdapter?.submitList(subscriptionPeriods)
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
                purchaseSubscriptionViewModel.refreshPurchaseSubscription(subscriptionId = arguments.subscriptionId)
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1)
            }
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonPurchaseSubscription.setOnClickListener { purchaseSubscription() }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        purchaseSubscriptionViewModel.purchaseSubscriptionUiState.observe(viewLifecycleOwner) { purchaseSubscriptionUiState ->
            purchaseSubscriptionUiState.apply {
                if (subscription != null) {
                    setSubscription(subscription = subscription)
                    setTotalPrice(
                        price = subscription.price,
                        durationInMonths = selectedSubscriptionPeriod
                    )
                    setBalance(balance = balance)
                    setLoading(isLoadingShown = isProgressBarPurchaseSubscriptionShown)
                    setErrorMessage(
                        errorMessage = errorMessage,
                        isErrorMessageShown = isErrorMessageShown
                    )
                    if (isSubscriptionPurchased) {
                        showToast(message = resources.getString(R.string.subscription_purchased_successfully))
                        goBack()
                    }
                }

                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = subscription == null
                binding.progressBarScreen.isVisible = isLoading && subscription == null
                binding.textViewScreenErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = isErrorMessageShown && subscription == null
                }
                binding.imageViewScreenLogo.isVisible = isErrorMessageShown && subscription == null
            }
        }
    }

    private fun setSubscription(subscription: Subscription) {
        binding.apply {
            textViewSubscriptionTitle.text = subscription.title
            textViewSubscriptionDescription.text = subscription.description
            textViewSubscriptionPrice.text = subscription.price.toString()
        }
    }

    private fun setTotalPrice(price: Long, durationInMonths: Int) {
        val totalPrice = price * durationInMonths
        binding.textViewTotalPrice.text = getString(R.string.total_price_with_number, totalPrice)
    }

    private fun setBalance(balance: Long) {
        binding.textViewBalance.text = getString(R.string.balance_with_number, balance)
    }

    private fun purchaseSubscription() {
        val subscriptionId = arguments.subscriptionId
        val durationInMonths = subscriptionPeriodAdapter
            ?.getSelectedPeriod() ?: DEFAULT_SUBSCRIPTION_PERIOD
        purchaseSubscriptionViewModel.purchaseSubscription(
            subscriptionId = subscriptionId,
            durationInMonths = durationInMonths
        )
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setLoading(isLoadingShown: Boolean) {
        binding.apply {
            progressBarPurchaseSubscription.isVisible = isLoadingShown
            buttonPurchaseSubscription.visibility =
                if (isLoadingShown) View.INVISIBLE else View.VISIBLE
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