package ru.ermakov.creator.presentation.screen.shared

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.ermakov.creator.databinding.FragmentOptionsBinding

class OptionsFragment(
    private val isEditShown: Boolean = false,
    private val isDeleteShown: Boolean = false,
    private val isCloseShown: Boolean = false,
) : BottomSheetDialogFragment() {
    private var _binding: FragmentOptionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.apply {
            textViewEdit.apply {
                isVisible = isEditShown
                setOnClickListener {
                    (requireParentFragment() as OptionsHandler).edit()
                    dismiss()
                }
            }
            textViewDelete.apply {
                isVisible = isDeleteShown
                setOnClickListener {
                    (requireParentFragment() as OptionsHandler).delete()
                    dismiss()
                }
            }
            textViewClose.apply {
                isVisible = isCloseShown
                setOnClickListener {
                    (requireParentFragment() as OptionsHandler).close()
                    dismiss()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}