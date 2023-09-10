package ru.ermakov.creator.presentation.fragment.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.ermakov.creator.app.CreatorApplication
import ru.ermakov.creator.databinding.FragmentEditProfileBinding
import ru.ermakov.creator.domain.model.SignInData
import ru.ermakov.creator.presentation.viewModel.account.editProfile.EditProfileViewModel
import ru.ermakov.creator.presentation.viewModel.account.editProfile.EditProfileViewModelFactory
import javax.inject.Inject

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    private val editProfileViewModel: EditProfileViewModel by viewModels {
        editProfileViewModelFactory
    }

    @Inject
    lateinit var editProfileViewModelFactory: EditProfileViewModelFactory

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
        binding.apply {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}