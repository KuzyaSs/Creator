package ru.ermakov.creator.presentation.screen.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.ermakov.creator.databinding.FragmentSettingsBinding
import ru.ermakov.creator.presentation.screen.CreatorActivity

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreatorActivity).hideBottomNavigationView()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.apply {
            textViewTitleWithBackButton.setOnClickListener { goBack() }
            textViewEditProfile.setOnClickListener { navigateToEditProfileFragment() }
            textViewChooseCategory.setOnClickListener { navigateToChooseCategoryFragment() }
            textViewChangePassword.setOnClickListener { navigateToChangePasswordFragment() }
        }
    }

    private fun navigateToEditProfileFragment() {
        val action = SettingsFragmentDirections.actionSettingsFragmentToEditProfileFragment()
        findNavController().navigate(action)
    }

    private fun navigateToChooseCategoryFragment() {
        val action = SettingsFragmentDirections.actionSettingsFragmentToChooseUserCategoryFragment()
        findNavController().navigate(action)
    }

    private fun navigateToChangePasswordFragment() {
        val action = SettingsFragmentDirections.actionSettingsFragmentToChangePasswordFragment()
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