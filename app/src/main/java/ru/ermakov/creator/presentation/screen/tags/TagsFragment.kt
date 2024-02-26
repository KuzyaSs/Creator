package ru.ermakov.creator.presentation.screen.tags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ru.ermakov.creator.R
import ru.ermakov.creator.databinding.FragmentTagsBinding
import ru.ermakov.creator.presentation.adapter.TagAdapter
import ru.ermakov.creator.presentation.screen.shared.OptionsHandler
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class TagsFragment : Fragment(), OptionsHandler {
    private var _binding: FragmentTagsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var tagsViewModelFactory: TagsViewModelFactory
    private lateinit var tagsViewModel: TagsViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    private var tagAdapter: TagAdapter? = null

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
    }

    override fun onStart() {
        super.onStart()
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

    }

    private fun setUpListeners() {

    }

    private fun setUpObservers() {

    }

    private fun setUpTagRecyclerView() {

    }

    private fun navigateToCreateTagFragment() {

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
        TODO("Not yet implemented")
    }

    override fun delete() {
        TODO("Not yet implemented")
    }

    override fun close() {}
}