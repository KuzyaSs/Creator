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
import ru.ermakov.creator.util.Constant.Companion.EMPTY_STRING
import ru.ermakov.creator.util.Constant.Companion.NETWORK_EXCEPTION
import ru.ermakov.creator.util.Resource
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
        splashViewModel.signInData.observe(viewLifecycleOwner) { signInDataResource ->
            when (signInDataResource) {
                is Resource.Success -> {
                    hideProgressBar()
                    navigateToFeedFragment()
                }

                is Resource.Error -> {
                    hideProgressBar()
                    if (signInDataResource.message == NETWORK_EXCEPTION) {
                        navigateToFeedFragment()
                        val message = exceptionLocalizer.localizeException(
                            message = signInDataResource.message
                        )
                        showToast(message = message)
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
        val action =
            SplashFragmentDirections.actionSplashFragmentToSignInFragment(email = EMPTY_STRING)
        findNavController().navigate(action)
    }

    private fun navigateToFeedFragment() {
        val action = SplashFragmentDirections.actionSplashFragmentToFeedFragment()
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