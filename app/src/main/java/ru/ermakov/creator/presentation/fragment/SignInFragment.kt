package ru.ermakov.creator.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentSignInBinding
import ru.ermakov.creator.presentation.viewModel.signIn.SignInViewModel
import ru.ermakov.creator.presentation.viewModel.signIn.SignInViewModelFactory
import javax.inject.Inject

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val signInViewModel: SignInViewModel by viewModels {
        signInViewModelFactory
    }

    @Inject
    lateinit var signInViewModelFactory: SignInViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.textViewForgotPassword.setOnClickListener { forgotPassword() }
        binding.buttonSignIn.setOnClickListener { signIn() }
        binding.textViewSignUp.setOnClickListener { signUp() }
    }

    private fun forgotPassword() {
        Toast.makeText(requireContext(), "*Forgot Password*", Toast.LENGTH_SHORT).show()
        /*        FirebaseAuth.getInstance().sendPasswordResetEmail("123").addOnSuccessListener {
                    Toast.makeText(requireContext(), "Update", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                }*/
    }

    private fun signIn() {
        Toast.makeText(requireContext(), "*Sign In*", Toast.LENGTH_SHORT).show()
        /*        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.textInputEditTextEmail.text.toString(),
                    binding.textInputEditTextPassword.text.toString()
                ).addOnSuccessListener {
                    Toast.makeText(requireContext(), "Sign In", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                }*/
    }

    private fun signUp() {
        val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}