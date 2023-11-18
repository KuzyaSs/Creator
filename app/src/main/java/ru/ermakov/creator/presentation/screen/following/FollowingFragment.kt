package ru.ermakov.creator.presentation.screen.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentFollowingBinding
import ru.ermakov.creator.domain.model.User
import ru.ermakov.creator.presentation.State
import ru.ermakov.creator.presentation.exception.ExceptionLocalizer
import ru.ermakov.creator.presentation.screen.CreatorActivity
import ru.ermakov.creator.presentation.screen.account.AccountFragment
import javax.inject.Inject

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var followingViewModelFactory: FollowingViewModelFactory
    private lateinit var followingViewModel: FollowingViewModel

    @Inject
    lateinit var exceptionLocalizer: ExceptionLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as CreatorActivity).showBottomNavigationView()
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        followingViewModel =
            ViewModelProvider(
                requireActivity(),
                followingViewModelFactory
            )[FollowingViewModel::class.java]
        setUpSwipeRefreshLayout()
        setUpListeners()
        setUpObservers()
    }

    private fun setUpSwipeRefreshLayout() {
        binding.swipeRefreshLayout.apply {
            setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorBackground
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

    private fun setUpListeners() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                followingViewModel.setCurrentUser()
                swipeRefreshLayout.isRefreshing = false
            }
            imageViewProfileAvatar.setOnClickListener {
                showAccountFragment()
            }
        }
    }

    private fun setUpObservers() {
        followingViewModel.followingUiState.observe(viewLifecycleOwner) { followingUiState ->
            when (followingUiState.state) {
                State.INITIAL -> {}
                State.SUCCESS -> {
                    hideProgressBar()
                    setProfileAvatar(followingUiState.currentUser!!)
                }

                State.ERROR -> {
                    hideProgressBar()
                    val errorMessage = exceptionLocalizer.localizeException(
                        errorMessage = followingUiState.errorMessage
                    )
                    showToast(errorMessage)
                }

                State.LOADING -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun setProfileAvatar(user: User) {
        if (user.profileAvatarUrl.isNotEmpty()) {
            Glide.with(binding.root)
                .load(user.profileAvatarUrl)
                .into(binding.imageViewProfileAvatar)
        }
    }

    private fun showAccountFragment() {
        AccountFragment().show(childFragmentManager, this.toString())
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgressBar() {
        binding.progressBar.isVisible = true
    }

    private fun hideProgressBar() {
        binding.progressBar.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}