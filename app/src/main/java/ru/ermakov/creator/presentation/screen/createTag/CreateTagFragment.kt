package ru.ermakov.creator.presentation.screen.createTag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentCreateTagBinding
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class CreateTagFragment : Fragment() {
    private val arguments: CreateTagFragmentArgs by navArgs()
    private var _binding: FragmentCreateTagBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var createTagViewModelFactory: CreateTagViewModelFactory
    private lateinit var createTagViewModel: CreateTagViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateTagBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        createTagViewModel = ViewModelProvider(
            this,
            createTagViewModelFactory
        )[CreateTagViewModel::class.java]
        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.apply {
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonCreate.setOnClickListener { createTag() }
        }
    }

    private fun setUpObservers() {
        createTagViewModel.createTagUiState.observe(viewLifecycleOwner) { createTagUiState ->
            createTagUiState.apply {
                setLoading(isLoadingShown = isProgressBarCreateShown)
                setErrorMessage(
                    errorMessage = errorMessage,
                    isErrorMessageShown = isErrorMessageShown
                )
                if (isTagCreated) {
                    showToast(message = resources.getString(R.string.tag_created_successfully))
                    goBack()
                }
            }
        }
    }

    private fun createTag() {
        val name = binding.textInputEditTextName.text?.trim().toString()
        createTagViewModel.createTag(arguments.creatorId, name = name)
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