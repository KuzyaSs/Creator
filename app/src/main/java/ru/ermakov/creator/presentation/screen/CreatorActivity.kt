package ru.ermakov.creator.presentation.screen

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
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

    fun showBottomNavigationView() {
        binding.bottomNavigationView.isVisible = true
    }

    fun hideBottomNavigationView() {
        binding.bottomNavigationView.isVisible = false
    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.setOnItemSelectedListener { selectedMenuItem ->
            val popped = popBackStackToFragment(navController, selectedMenuItem)
            if (!popped) {
                when (selectedMenuItem.itemId) {
                    R.id.followingFragment -> {
                        navController.navigate(R.id.followingFragment)
                        return@setOnItemSelectedListener true
                    }

                    R.id.discoverFragment -> {
                        navController.navigate(R.id.discoverFragment)
                        return@setOnItemSelectedListener true
                    }

                    R.id.searchFragment -> {
                        navController.navigate(R.id.searchFragment)
                        return@setOnItemSelectedListener true
                    }
                }
            }
            return@setOnItemSelectedListener false
        }
    }

    // Go to the fragment if it was previously created.
    private fun popBackStackToFragment(
        navController: NavController,
        selectedMenuItem: MenuItem
    ): Boolean {
        var isFound = false
        val currentBackStack = navController.currentBackStack.value
        for (navBackStackEntry in currentBackStack) {
            if (isFound) {
                navController.popBackStack()
                continue
            }
            if (navBackStackEntry.destination.id == selectedMenuItem.itemId) {
                isFound = true
            }
        }
        return isFound
    }
}