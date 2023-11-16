package ru.ermakov.creator.presentation.screen.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentSplashBinding
import ru.ermakov.creator.presentation.exception.ExceptionLocalizer
import ru.ermakov.creator.domain.exception.ErrorConstants.Companion.NETWORK_EXCEPTION
import ru.ermakov.creator.presentation.State
import javax.inject.Inject

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var splashViewModelFactory: SplashViewModelFactory
    private lateinit var splashViewModel: SplashViewModel

    @Inject
    lateinit var exceptionLocalizer: ExceptionLocalizer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        splashViewModel =
            ViewModelProvider(this, splashViewModelFactory)[SplashViewModel::class.java]
        setUpObservers()
        splashViewModel.checkSignedInStatus()
    }

    private fun setUpObservers() {
        splashViewModel.splashUiState.observe(viewLifecycleOwner) { splashUiState ->
            when (splashUiState.state) {
                State.INITIAL -> {}
                State.SUCCESS -> {
                    hideProgressBar()
                    navigateToFollowingFragment()
                }

                State.ERROR -> {
                    hideProgressBar()
                    if (splashUiState.errorMessage == NETWORK_EXCEPTION) {
                        val errorMessage = exceptionLocalizer.localizeException(
                            errorMessage = splashUiState.errorMessage
                        )
                        showToast(message = errorMessage)
                        navigateToFollowingFragment()
                    } else {
                        navigateToSignInFragment()
                    }
                }

                State.LOADING -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun navigateToSignInFragment() {
        val action =
            SplashFragmentDirections.actionSplashFragmentToSignInFragment("")
        findNavController().navigate(action)
    }

    private fun navigateToFollowingFragment() {
        val action = SplashFragmentDirections.actionSplashFragmentToFollowingFragment()
        findNavController().navigate(action)
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