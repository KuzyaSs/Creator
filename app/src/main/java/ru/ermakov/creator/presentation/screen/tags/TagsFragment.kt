package ru.ermakov.creator.presentation.screen.tags

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.DialogDeleteTagBinding
import ru.ermakov.creator.databinding.FragmentTagsBinding
import ru.ermakov.creator.domain.model.Tag
import ru.ermakov.creator.presentation.adapter.TagAdapter
import ru.ermakov.creator.presentation.screen.shared.OptionsFragment
import ru.ermakov.creator.presentation.screen.shared.OptionsHandler
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class TagsFragment : Fragment(), OptionsHandler {
    private val arguments: TagsFragmentArgs by navArgs()
    private var _binding: FragmentTagsBinding? = null
    private val binding get() = _binding!!
    private var _dialogDeleteTagBinding: DialogDeleteTagBinding? = null

    @Inject
    lateinit var tagsViewModelFactory: TagsViewModelFactory
    private lateinit var tagsViewModel: TagsViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var tagAdapter: TagAdapter? = null
    private var deleteTagDialog: Dialog? = null
    private var optionsFragment: OptionsFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTagsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        tagsViewModel = ViewModelProvider(
            this,
            tagsViewModelFactory
        )[TagsViewModel::class.java]
        if (tagsViewModel.tagsUiState.value?.tags == null) {
            tagsViewModel.setTagsScreen(creatorId = arguments.creatorId)
        }
        optionsFragment = OptionsFragment(isEditShown = true, isDeleteShown = true)
        setUpSwipeRefreshLayout()
        setDeleteTagDialog()
        setUpListeners()
        setUpObservers()
    }

    override fun onStart() {
        super.onStart()
        if (tagsViewModel.tagsUiState.value?.tags != null) {
            tagsViewModel.refreshTagsScreen(creatorId = arguments.creatorId)
        }
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

    private fun setDeleteTagDialog() {
        _dialogDeleteTagBinding = DialogDeleteTagBinding.inflate(layoutInflater)
        deleteTagDialog = Dialog(requireContext())
        deleteTagDialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(_dialogDeleteTagBinding?.root!!)
            setCancelable(false)
            _dialogDeleteTagBinding?.textViewNo?.setOnClickListener {
                deleteTagDialog?.dismiss()
            }
            _dialogDeleteTagBinding?.textViewYes?.setOnClickListener {
                tagsViewModel.deleteSelectedTag()
                deleteTagDialog?.dismiss()
            }
        }
    }

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                tagsViewModel.refreshTagsScreen(creatorId = arguments.creatorId)
            }
            swipeRefreshLayout.setOnChildScrollUpCallback { _, _ ->
                scrollView.canScrollVertically(-1) || recyclerViewTags.canScrollVertically(-1)
            }
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            buttonCreate.setOnClickListener { navigateToCreateTagFragment() }
            viewLoading.setOnClickListener { }
        }
    }

    private fun setUpObservers() {
        tagsViewModel.tagsUiState.observe(viewLifecycleOwner) { tagsUiState ->
            tagsUiState.apply {
                if (tags != null) {
                    setUpTagRecyclerView(tags = tags)
                    setEmptyListInfo(isEmptyList = tags.isEmpty())
                    setErrorMessage(
                        errorMessage = textLocalizer.localizeText(text = errorMessage),
                        isErrorMessageShown = isErrorMessageShown
                    )
                }

                binding.swipeRefreshLayout.isRefreshing = isRefreshingShown
                binding.viewLoading.isVisible = tags == null
                binding.progressBarScreen.isVisible = !isErrorMessageShown && tags == null
                binding.textViewScreenErrorMessage.apply {
                    text = textLocalizer.localizeText(text = errorMessage)
                    isVisible = isErrorMessageShown && tags == null
                }
                binding.imageViewScreenLogo.isVisible = isErrorMessageShown && tags == null
            }
        }
    }

    private fun setUpTagRecyclerView(tags: List<Tag>) {
        tagAdapter = TagAdapter(onMoreClickListener = { tag ->
            tagsViewModel.setSelectedTagId(tagId = tag.id)
            optionsFragment?.show(childFragmentManager, optionsFragment.toString())
        })
        binding.recyclerViewTags.adapter = tagAdapter
        tagAdapter?.submitList(tags)
    }

    private fun navigateToCreateTagFragment() {
        val action = TagsFragmentDirections.actionTagsFragmentToCreateTagFragment(
            creatorId = arguments.creatorId
        )
        findNavController().navigate(action)
    }

    private fun navigateToEditTagFragment() {
        val action = TagsFragmentDirections.actionTagsFragmentToEditTagFragment(
            tagId = tagsViewModel.tagsUiState.value?.selectedTagId ?: UNSELECTED_TAG_ID
        )
        findNavController().navigate(action)
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
            showToast(message = errorMessage)
            tagsViewModel.clearErrorMessage()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun edit() {
        navigateToEditTagFragment()
    }

    override fun delete() {
        deleteTagDialog?.show()
    }

    override fun close() {}
}