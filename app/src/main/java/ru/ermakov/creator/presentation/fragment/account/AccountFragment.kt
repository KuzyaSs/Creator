package ru.ermakov.creator.presentation.fragment.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.ermakov.creator.databinding.FragmentAccountBinding
import ru.ermakov.creator.presentation.activity.CreatorActivity

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreatorActivity).showBottomNavigationView()
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.apply {
            textViewMyBlog.setOnClickListener { navigateToMyBlogFragment() }
            textViewSubscriptions.setOnClickListener { navigateToSubscriptionsFragment() }
            textViewAccountSettings.setOnClickListener { navigateToAccountSettingsFragment() }
            textViewWallet.setOnClickListener { navigateToWalletFragment() }
            textViewSupport.setOnClickListener { navigateToSupportFragment() }
            textViewFiles.setOnClickListener { navigateToFilesFragment() }
        }
    }

    private fun navigateToMyBlogFragment() {

    }

    private fun navigateToSubscriptionsFragment() {

    }

    private fun navigateToAccountSettingsFragment() {
        val action = AccountFragmentDirections.actionAccountFragmentToAccountSettingsFragment()
        findNavController().navigate(action)
    }

    private fun navigateToWalletFragment() {

    }

    private fun navigateToSupportFragment() {

    }

    private fun navigateToFilesFragment() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}