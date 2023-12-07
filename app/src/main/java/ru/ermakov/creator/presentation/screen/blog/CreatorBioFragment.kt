package ru.ermakov.creator.presentation.screen.blog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentCreatorBioBinding
import ru.ermakov.creator.presentation.util.TextLocalizer
import javax.inject.Inject

class CreatorBioFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentCreatorBioBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var blogViewModelFactory: BlogViewModelFactory
    private lateinit var blogViewModel: BlogViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreatorBioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        blogViewModel = ViewModelProvider(
            requireParentFragment(),
            blogViewModelFactory
        )[BlogViewModel::class.java]
        setUpObservers()
    }

    private fun setUpObservers() {
        blogViewModel.blogUiState.observe(viewLifecycleOwner) { blogUiState ->
            blogUiState.apply {
                val bio = creator?.user?.bio.toString()
                if (bio.isNotBlank()) {
                    binding.textViewBio.text = creator?.user?.bio.toString()
                } else {
                    binding.textViewBio.text = resources.getString(
                        R.string.empty_bio,
                        creator?.user?.username.toString()
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}