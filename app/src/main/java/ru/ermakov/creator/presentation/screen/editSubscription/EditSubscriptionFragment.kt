package ru.ermakov.creator.presentation.screen.editSubscription

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
import ru.ermakov.creator.databinding.FragmentEditSubscriptionBinding
import ru.ermakov.creator.domain.model.Subscription
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class EditSubscriptionFragment : Fragment() {
    private val arguments: EditSubscriptionFragmentArgs by navArgs()
    private var _binding: FragmentEditSubscriptionBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var editSubscriptionViewModelFactory: EditSubscriptionViewModelFactory
    private lateinit var editSubscriptionViewModel: EditSubscriptionViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditSubscriptionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        editSubscriptionViewModel = ViewModelProvider(
            this,
            editSubscriptionViewModelFactory
        )[EditSubscriptionViewModel::class.java]
        if (editSubscriptionViewModel.editSubscriptionUiState.value?.subscription == null) {
            editSubscriptionViewModel.setSubscription(subscriptionId = arguments.subscriptionId)
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
                editSubscriptionViewModel.refreshSubscription(subscriptionId = arguments.subscriptionId)
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1)
            }
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonSaveChanges.setOnClickListener { editSubscription() }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        editSubscriptionViewModel.editSubscriptionUiState.observe(viewLifecycleOwner) { createSubscriptionUiState ->
            createSubscriptionUiState.apply {
                if (subscription != null) {
                    setSubscription(subscription = subscription)
                    setLoading(isLoadingShown = isProgressBarSaveChangesShown)
                    setErrorMessage(
                        errorMessage = errorMessage,
                        isErrorMessageShown = isErrorMessageShown
                    )
                    if (isSubscriptionEdited) {
                        showToast(message = resources.getString(R.string.subscription_edited_successfully))
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
        if (binding.textInputEditTextTitle.text.isNullOrBlank()) {
            binding.textInputEditTextTitle.setText(subscription.title)
        }
        if (binding.textInputEditTextDescription.text.isNullOrBlank()) {
            binding.textInputEditTextDescription.setText(subscription.description)
        }
        if (binding.textInputEditTextPrice.text.isNullOrBlank()) {
            binding.textInputEditTextPrice.setText(subscription.price.toString())
        }
    }

    private fun editSubscription() {
        val title = binding.textInputEditTextTitle.text.toString()
        val description = binding.textInputEditTextDescription.text.toString()
        val price = binding.textInputEditTextPrice.text.toString().toLongOrNull() ?: 0
        editSubscriptionViewModel.editSubscription(
            subscriptionId = arguments.subscriptionId,
            title = title,
            description = description,
            price = price
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