package ru.ermakov.creator.presentation.screen.createSubscription

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
import ru.ermakov.creator.databinding.FragmentCreateSubscriptionBinding
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class CreateSubscriptionFragment : Fragment() {
    private var _binding: FragmentCreateSubscriptionBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var createSubscriptionViewModelFactory: CreateSubscriptionViewModelFactory
    private lateinit var createSubscriptionViewModel: CreateSubscriptionViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateSubscriptionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        createSubscriptionViewModel = ViewModelProvider(
            this,
            createSubscriptionViewModelFactory
        )[CreateSubscriptionViewModel::class.java]
        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.apply {
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonCreate.setOnClickListener { createSubscription() }
        }
    }

    private fun setUpObservers() {
        createSubscriptionViewModel.createSubscriptionUiState.observe(viewLifecycleOwner) { createSubscriptionUiState ->
            createSubscriptionUiState.apply {
                setLoading(isLoadingShown = isProgressBarCreateShown)
                setErrorMessage(
                    errorMessage = errorMessage,
                    isErrorMessageShown = isErrorMessageShown
                )
                if (isSubscriptionCreated) {
                    showToast(message = resources.getString(R.string.subscription_created_successfully))
                    goBack()
                }
            }
        }
    }

    private fun createSubscription() {
        val title = binding.textInputEditTextTitle.text?.trim().toString()
        val description = binding.textInputEditTextDescription.text?.trim().toString()
        val price = binding.textInputEditTextPrice.text.toString().toLongOrNull() ?: 0
        createSubscriptionViewModel.createSubscription(
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