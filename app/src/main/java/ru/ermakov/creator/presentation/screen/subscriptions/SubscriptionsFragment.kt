package ru.ermakov.creator.presentation.screen.subscriptions

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
import ru.ermakov.creator.databinding.FragmentSubscriptionsBinding
import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.domain.model.UserSubscription
import ru.ermakov.creator.presentation.adapter.SubscriptionAdapter
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class SubscriptionsFragment : Fragment() {
    private val arguments: SubscriptionsFragmentArgs by navArgs()
    private var _binding: FragmentSubscriptionsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var subscriptionsViewModelFactory: SubscriptionsViewModelFactory
    private lateinit var subscriptionsViewModel: SubscriptionsViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var subscriptionAdapter: SubscriptionAdapter? = null

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
            requireParentFragment(),
            subscriptionsViewModelFactory
        )[SubscriptionsViewModel::class.java]
        if (subscriptionsViewModel.subscriptionsUiState.value?.subscriptions == null) {
            subscriptionsViewModel.setSubscriptions(creatorId = arguments.creatorId)
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
                subscriptionsViewModel.refreshSubscriptions(creatorId = arguments.creatorId)
            }
            buttonCreate.setOnClickListener { navigateToCreateSubscriptionFragment() }
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
                    setErrorMessage(
                        errorMessage = textLocalizer.localizeText(text = errorMessage),
                        isErrorMessageShown = isErrorMessageShown
                    )
                }

                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = subscriptions == null
                binding.progressBar.isVisible = !isErrorMessageShown && subscriptions == null
                binding.textViewErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = isErrorMessageShown && subscriptions == null
                }
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
            onSubscribeButtonClickListener = { subscription ->
                navigateToBuySubscriptionFragment(subscriptionId = subscription.id)
                Toast.makeText(requireContext(), "Subscribe", Toast.LENGTH_SHORT).show()
            },
            onUnsubscribeButtonClickListener = { subscription ->
                Toast.makeText(requireContext(), "Unsubscribe", Toast.LENGTH_SHORT).show()
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

    private fun navigateToBuySubscriptionFragment(subscriptionId: Long) {
        /*        val action = SearchFragmentDirections.actionSearchFragmentToBlogFragment(
                    subscriptionId = subscriptionId
                )
                findNavController().navigate(action)*/
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
}