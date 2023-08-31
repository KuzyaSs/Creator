package ru.ermakov.creator.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.ermakov.creator.R
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentSplashBinding
import ru.ermakov.creator.presentation.viewModel.splash.SplashViewModel
import ru.ermakov.creator.presentation.viewModel.splash.SplashViewModelFactory
import ru.ermakov.creator.util.Constant.Companion.EMPTY_STRING
import ru.ermakov.creator.util.Constant.Companion.NETWORK_EXCEPTION
import ru.ermakov.creator.util.Resource
import javax.inject.Inject

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val splashViewModel: SplashViewModel by viewModels {
        splashViewModelFactory
    }

    @Inject
    lateinit var splashViewModelFactory: SplashViewModelFactory

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
        setUpObservers()
        splashViewModel.checkSignedInStatus()
    }

    private fun setUpObservers() {
        splashViewModel.signInData.observe(viewLifecycleOwner) { signInDataResource ->
            when (signInDataResource) {
                is Resource.Success -> {
                    hideProgressBar()
                    navigateToFeedFragment()
                }

                is Resource.Error -> {
                    hideProgressBar()
                    if (signInDataResource.data != null) {
                        navigateToFeedFragment()
                        val errorMessage = localizeError(
                            errorMessage = signInDataResource.message ?: EMPTY_STRING
                        )
                        showToast(message = errorMessage)
                    } else {
                        navigateToSignInFragment()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun navigateToSignInFragment() {
        val action = SplashFragmentDirections.actionSplashFragmentToSignInFragment(email = EMPTY_STRING)
        findNavController().navigate(action)
    }

    private fun navigateToFeedFragment() {
        val action = SplashFragmentDirections.actionSplashFragmentToFeedFragment()
        findNavController().navigate(action)
    }

    private fun localizeError(errorMessage: String): String {
        return when (errorMessage) {
            NETWORK_EXCEPTION -> {
                getString(R.string.offline_mode)
            }

            else -> {
                getString(R.string.unknown_exception)
            }
        }
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