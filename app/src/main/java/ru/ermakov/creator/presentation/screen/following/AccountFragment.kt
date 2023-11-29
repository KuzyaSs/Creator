package ru.ermakov.creator.presentation.screen.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentAccountBinding
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.presentation.util.State
import ru.ermakov.creator.presentation.util.TextLocalizer
import ru.ermakov.creator.presentation.screen.CreatorActivity
import javax.inject.Inject

class AccountFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var followingViewModelFactory: FollowingViewModelFactory
    private lateinit var followingViewModel: FollowingViewModel

    @Inject
    lateinit var textLocalizer: TextLocalizer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        followingViewModel = ViewModelProvider(
            requireActivity(), followingViewModelFactory
        )[FollowingViewModel::class.java]
        (activity as CreatorActivity).showBottomNavigationView()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpListeners() {
        binding.apply {
            textViewMyBlog.setOnClickListener { navigateToMyBlogFragment() }
            textViewStatistics.setOnClickListener { navigateToStatisticsFragment() }
            textViewCredits.setOnClickListener { navigateToCreditsFragment() }
            textViewDownloads.setOnClickListener { navigateToDownloadsFragment() }
            textViewSettings.setOnClickListener { navigateToSettingsFragment() }
            textViewSignOut.setOnClickListener { signOut() }
        }
    }

    private fun setUpObservers() {
        followingViewModel.followingUiState.observe(viewLifecycleOwner) { followingUiState ->
            when (followingUiState.state) {
                State.INITIAL -> {}
                State.SUCCESS -> {
                    setUserProfile(followingUiState.currentUser!!)
                }

                State.ERROR -> {
                    val errorMessage = textLocalizer.localizeText(
                        text = followingUiState.errorMessage
                    )
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }

                State.LOADING -> {

                }
            }
        }
    }

    private fun setUserProfile(user: User) {
        binding.apply {
            Glide.with(binding.root)
                .load(user.profileAvatarUrl)
                .placeholder(R.drawable.default_profile_avatar)
                .into(imageViewProfileAvatar)
            textViewUsername.text = user.username
        }
    }

    private fun navigateToMyBlogFragment() {
        val action = FollowingFragmentDirections.actionFollowingFragmentToBlogFragment()
        findNavController().navigate(action)
    }

    private fun navigateToStatisticsFragment() {

    }

    private fun navigateToCreditsFragment() {

    }

    private fun navigateToDownloadsFragment() {

    }

    private fun navigateToSettingsFragment() {
        val action = FollowingFragmentDirections.actionFollowingFragmentToSettingsFragment()
        findNavController().navigate(action)
    }

    private fun signOut() {
        followingViewModel.signOut()
        activity?.viewModelStore?.clear()
        val action = FollowingFragmentDirections.actionFollowingFragmentToSignInFragment(null)
        findNavController().navigate(action)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}