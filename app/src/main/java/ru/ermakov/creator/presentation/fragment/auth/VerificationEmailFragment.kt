package ru.ermakov.creator.presentation.fragment.auth

import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.ermakov.creator.R
import ru.ermakov.creator.databinding.FragmentVerificationEmailBinding

class VerificationEmailFragment : Fragment() {
    private val arguments: VerificationEmailFragmentArgs by navArgs()

    private var _binding: FragmentVerificationEmailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerificationEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListeners()
        setUpEmailVerificationDescription()
    }

    private fun setUpListeners() {
        binding.apply {
            imageViewBack.setOnClickListener { goBack() }
            buttonContinue.setOnClickListener { navigateToSignInFragment(arguments.email) }
        }
    }

    private fun setUpEmailVerificationDescription() {
        val text: String = getString(R.string.email_description, arguments.email)
        val styledText: Spanned = Html.fromHtml(text, FROM_HTML_MODE_LEGACY)
        binding.textViewEmailVerificationDescription.text = styledText
    }

    private fun navigateToSignInFragment(email: String) {
        val action = VerificationEmailFragmentDirections
                .actionVerificationEmailFragmentToSignInFragment(email = email)
        findNavController().navigate(action)
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}