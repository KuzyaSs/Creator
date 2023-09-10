package ru.ermakov.creator.presentation.fragment.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.ermakov.creator.databinding.FragmentAccountSettingsBinding
import ru.ermakov.creator.presentation.activity.CreatorActivity

class AccountSettingsFragment : Fragment() {
    private var _binding: FragmentAccountSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreatorActivity).hideBottomNavigationView()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.apply {
            imageViewBack.setOnClickListener { goBack() }
            textViewEditProfile.setOnClickListener { navigateToEditProfileFragment() }
            textViewChangePassword.setOnClickListener { navigateToChangePasswordFragment() }
        }
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun navigateToEditProfileFragment() {
        val action = AccountSettingsFragmentDirections
            .actionAccountSettingsFragmentToEditProfileFragment()
        findNavController().navigate(action)
    }

    private fun navigateToChangePasswordFragment() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}