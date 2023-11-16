package ru.ermakov.creator.presentation.screen.account

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentAccountBinding
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.presentation.State
import ru.ermakov.creator.presentation.exception.ExceptionLocalizer
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.passwordRecovery.PasswordRecoveryViewModel
import ru.ermakov.creator.presentation.screen.passwordRecovery.PasswordRecoveryViewModelFactory
import javax.inject.Inject

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var accountViewModelFactory: AccountViewModelFactory
    private lateinit var accountViewModel: AccountViewModel

    @Inject
    lateinit var exceptionLocalizer: ExceptionLocalizer

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
        accountViewModel =
            ViewModelProvider(this, accountViewModelFactory)[AccountViewModel::class.java]
        (activity as CreatorActivity).showBottomNavigationView()
        setUpListeners()
        setUpObservers()
        accountViewModel.setUser()
    }

    private fun setUpListeners() {
        binding.apply {
            textViewMyBlog.setOnClickListener { navigateToMyBlogFragment() }
            textViewSettings.setOnClickListener { navigateToAccountSettingsFragment() }
            textViewStatistics.setOnClickListener { navigateToWalletFragment() }
            textViewDownloads.setOnClickListener { navigateToFilesFragment() }
        }
    }

    private fun setUpObservers() {
        accountViewModel.accountUiState.observe(viewLifecycleOwner) { accountUiState ->
            when (accountUiState.state) {
                State.INITIAL -> {}
                State.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    setProfile(accountUiState.currentUser!!)
                }

                State.ERROR -> {
                    val errorMessage = exceptionLocalizer.localizeException(
                        errorMessage = accountUiState.errorMessage
                    )
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }

                State.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setProfile(user: User) {
        binding.apply {
            Glide.with(binding.root)
                .load(user.profileAvatarUrl)
                .placeholder(R.drawable.default_profile_avatar)
                .into(imageViewProfileAvatar)
            textViewUsername.text = user.username
        }
    }

    private fun navigateToMyBlogFragment() {

    }

    private fun navigateToSubscriptionsFragment() {

    }

    private fun navigateToAccountSettingsFragment() {
        val action = AccountFragmentDirections.actionAccountFragmentToSettingsFragment()
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
        Log.d("MY_TAG", "onDestroyView")
    }
}