package ru.ermakov.creator.presentation.screen.editTag

import android.os.Bundle
import android.util.Log
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
import ru.ermakov.creator.databinding.FragmentEditTagBinding
import ru.ermakov.creator.domain.model.Tag
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class EditTagFragment : Fragment() {
    private val arguments: EditTagFragmentArgs by navArgs()
    private var _binding: FragmentEditTagBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var editTagViewModelFactory: EditTagViewModelFactory
    private lateinit var editTagViewModel: EditTagViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTagBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        editTagViewModel = ViewModelProvider(
            this,
            editTagViewModelFactory
        )[EditTagViewModel::class.java]
        if (editTagViewModel.editTagUiState.value?.tag == null) {
            editTagViewModel.setEditTagScreen(tagId = arguments.tagId)
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
                editTagViewModel.refreshEditTagScreen(tagId = arguments.tagId)
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1)
            }
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonSaveChanges.setOnClickListener { editTag() }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        editTagViewModel.editTagUiState.observe(viewLifecycleOwner) { editTagUiState ->
            editTagUiState.apply {
                if (tag != null) {
                    setTag(tag = tag)
                    setLoading(isLoadingShown = isProgressBarSaveChangesShown)
                    setErrorMessage(
                        errorMessage = errorMessage,
                        isErrorMessageShown = isErrorMessageShown
                    )
                    if (isTagEdited) {
                        showToast(message = resources.getString(R.string.tag_edited_successfully))
                        goBack()
                    }
                }

                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = tag == null
                binding.progressBarScreen.isVisible = isLoadingShown && tag == null
                val isErrorVisible = isErrorMessageShown && tag == null
                binding.imageViewScreenLogo.isVisible = isErrorVisible
                binding.textViewScreenErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = isErrorVisible
                }
            }
        }
    }

    private fun setTag(tag: Tag) {
        if (binding.textInputEditTextName.text.isNullOrBlank()) {
            binding.textInputEditTextName.setText(tag.name)
        }
    }

    private fun editTag() {
        val name = binding.textInputEditTextName.text?.trim().toString()
        editTagViewModel.editTag(tagId = arguments.tagId, name = name)
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