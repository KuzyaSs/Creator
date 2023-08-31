package ru.ermakov.creator.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import ru.ermakov.creator.R
import ru.ermakov.creator.databinding.ActivityCreatorBinding

class CreatorActivity : AppCompatActivity() {
    private var _binding: ActivityCreatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCreatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupWithNavController(binding.bottomNavigationView, navController)
    }

    fun showBottomNavigationView() {
        binding.bottomNavigationView.isVisible = true
    }

    fun hideBottomNavigationView() {
        binding.bottomNavigationView.isVisible = false
    }
}