package ru.ermakov.creator.presentation.screen.account

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentAccountBinding
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.util.Resource
import javax.inject.Inject

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    private val accountViewModel: AccountViewModel by viewModels {
        accountViewModelFactory
    }

    @Inject
    lateinit var accountViewModelFactory: AccountViewModelFactory

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
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        (activity as CreatorActivity).showBottomNavigationView()
        setUpListeners()
        setUpObservers()
        accountViewModel.setUser()
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

    private fun setUpObservers() {
        accountViewModel.user.observe(viewLifecycleOwner) { userResource ->
            when (userResource) {
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    userResource.data?.let { user ->
                        setProfile(user = user)
                    }
                }

                is Resource.Error -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setProfile(user: User) {
        binding.apply {
            if (user.profileAvatar.isNotEmpty()) {
                imageViewProfileAvatar.setImageURI(Uri.parse(user.profileAvatar))
            } else {
                imageViewProfileAvatar.setImageResource(R.drawable.default_profile_avatar)
            }
            if (user.profileBackground.isNotEmpty()) {
                imageViewProfileBackground.setImageURI(Uri.parse(user.profileBackground))
            } else {
                imageViewProfileBackground.setImageResource(R.drawable.default_profile_background)
            }
            textViewUsername.text = user.username
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
        accountViewModel.detachUserListeners()
    }
}