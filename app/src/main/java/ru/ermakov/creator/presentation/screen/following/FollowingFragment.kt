package ru.ermakov.creator.presentation.screen.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import ru.ermakov.creator.databinding.FragmentFollowingBinding
import ru.ermakov.creator.presentation.screen.CreatorActivity

class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

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
        binding.textViewTitle.setOnClickListener { FirebaseAuth.getInstance().signOut() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}