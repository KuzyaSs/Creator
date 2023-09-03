package ru.ermakov.creator.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import ru.ermakov.creator.databinding.FragmentFeedBinding
import ru.ermakov.creator.presentation.activity.CreatorActivity

class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
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