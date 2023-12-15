package ru.ermakov.creator.presentation.screen.subscriptions

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.DialogUnsubscribeBinding
import ru.ermakov.creator.databinding.FragmentSubscriptionsBinding
import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.domain.model.UserSubscription
import ru.ermakov.creator.presentation.adapter.SubscriptionAdapter
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.shared.OptionsFragment
import ru.ermakov.creator.presentation.screen.shared.OptionsHandler
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class SubscriptionsFragment : Fragment(), OptionsHandler {
    private val arguments: SubscriptionsFragmentArgs by navArgs()
    private var _binding: FragmentSubscriptionsBinding? = null
    private val binding get() = _binding!!
    private var _dialogUnsubscribeBinding: DialogUnsubscribeBinding? = null

    @Inject
    lateinit var subscriptionsViewModelFactory: SubscriptionsViewModelFactory
    private lateinit var subscriptionsViewModel: SubscriptionsViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var subscriptionAdapter: SubscriptionAdapter? = null
    private var unsubscribeDialog: Dialog? = null
    private var optionsFragment: OptionsFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubscriptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreatorActivity).hideBottomNavigationView()
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        subscriptionsViewModel = ViewModelProvider(
            this,
            subscriptionsViewModelFactory
        )[SubscriptionsViewModel::class.java]
        if (subscriptionsViewModel.subscriptionsUiState.value?.subscriptions == null) {
            subscriptionsViewModel.setSubscriptions(creatorId = arguments.creatorId)
        }
        optionsFragment = OptionsFragment()
        setUpSwipeRefreshLayout()
        setUnsubscribeDialog()
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

    private fun setUnsubscribeDialog() {
        _dialogUnsubscribeBinding = DialogUnsubscribeBinding.inflate(layoutInflater)
        unsubscribeDialog = Dialog(requireContext())
        unsubscribeDialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(_dialogUnsubscribeBinding?.root!!)
            setCancelable(false)
            _dialogUnsubscribeBinding?.textViewNo?.setOnClickListener { unsubscribeDialog?.dismiss() }
            _dialogUnsubscribeBinding?.textViewYes?.setOnClickListener {
                subscriptionsViewModel.unsubscribe()
                unsubscribeDialog?.dismiss()
            }
        }
    }

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                subscriptionsViewModel.refreshSubscriptions(creatorId = arguments.creatorId)
            }
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonCreate.setOnClickListener { navigateToCreateSubscriptionFragment() }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        subscriptionsViewModel.subscriptionsUiState.observe(viewLifecycleOwner) { subscriptionsUiState ->
            subscriptionsUiState.apply {
                if (subscriptions != null && userSubscriptions != null) {
                    val isOwner = currentUserId == creatorId
                    binding.buttonCreate.isVisible = isOwner
                    setUpCreatorRecyclerView(
                        subscriptions = subscriptions,
                        userSubscriptions = userSubscriptions,
                        isOwner = isOwner
                    )
                    setEmptyListInfo(isEmptyList = subscriptions.isEmpty())
                    binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                    setErrorMessage(
                        errorMessage = textLocalizer.localizeText(text = errorMessage),
                        isErrorMessageShown = isErrorMessageShown
                    )
                }

                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = subscriptions == null
                binding.progressBarScreen.isVisible = !isErrorMessageShown && subscriptions == null
                binding.textViewErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = isErrorMessageShown && subscriptions == null
                }
                binding.imageViewScreenLogo.isVisible = isErrorMessageShown && subscriptions == null
            }
        }
    }

    private fun setUpCreatorRecyclerView(
        subscriptions: List<Subscription>,
        userSubscriptions: List<UserSubscription>,
        isOwner: Boolean
    ) {
        subscriptionAdapter = SubscriptionAdapter(
            userSubscriptions = userSubscriptions,
            isOwner = isOwner,
            onMoreImageViewClickListener = { subscription ->
                optionsFragment?.show(childFragmentManager, optionsFragment.toString())
            },
            onSubscribeButtonClickListener = { subscription ->
                navigateToPurchaseSubscriptionFragment(subscriptionId = subscription.id)
            },
            onUnsubscribeButtonClickListener = { userSubscription ->
                subscriptionsViewModel.setSelectedUserSubscriptionId(
                    userSubscriptionId = userSubscription.id
                )
                unsubscribeDialog?.show()
            })
        binding.recyclerViewSubscriptions.adapter = subscriptionAdapter
        subscriptionAdapter?.submitList(subscriptions)
    }

    private fun navigateToCreateSubscriptionFragment() {
        /*        val action = SearchFragmentDirections.actionSearchFragmentToBlogFragment(
                    creatorId = creatorId
                )
                findNavController().navigate(action)*/
    }

    private fun navigateToPurchaseSubscriptionFragment(subscriptionId: Long) {
        /*        val action = SearchFragmentDirections.actionSearchFragmentToBlogFragment(
                    subscriptionId = subscriptionId
                )
                findNavController().navigate(action)*/
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun setEmptyListInfo(isEmptyList: Boolean) {
        binding.imageViewLogo.isVisible = isEmptyList
        binding.textViewEmptyList.isVisible = isEmptyList
    }

    private fun setErrorMessage(errorMessage: String, isErrorMessageShown: Boolean) {
        if (isErrorMessageShown) {
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            subscriptionsViewModel.clearErrorMessage()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun edit() {
        Toast.makeText(requireContext(), "edit", Toast.LENGTH_SHORT).show()
    }

    override fun delete() {
        Toast.makeText(requireContext(), "delete", Toast.LENGTH_SHORT).show()
    }
}