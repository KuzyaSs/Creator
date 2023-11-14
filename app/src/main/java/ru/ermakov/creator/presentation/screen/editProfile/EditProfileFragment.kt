package ru.ermakov.creator.presentation.screen.editProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentEditProfileBinding
import ru.ermakov.creator.presentation.exception.ExceptionLocalizer
import ru.ermakov.creator.presentation.screen.account.AccountViewModel
import ru.ermakov.creator.presentation.screen.account.AccountViewModelFactory
import javax.inject.Inject

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var editProfileViewModelFactory: EditProfileViewModelFactory
    private lateinit var editProfileViewModel: EditProfileViewModel

    @Inject
    lateinit var exceptionLocalizer: ExceptionLocalizer


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as CreatorApplication).applicationComponent.inject(fragment = this)
        editProfileViewModel =
            ViewModelProvider(this, editProfileViewModelFactory)[EditProfileViewModel::class.java]
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.apply {
            imageViewBack.setOnClickListener { goBack() }
            // buttonEditProfile.setOnClickListener { editProfile() }
        }
    }

    private fun goBack() {
        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    private fun editProfile() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}