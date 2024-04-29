package com.sopt.now.feature

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sopt.now.R
import com.sopt.now.core.base.BindingActivity
import com.sopt.now.core.util.context.snackBar
import com.sopt.now.databinding.ActivityMainBinding
import com.sopt.now.feature.util.KeyStorage.TOTAL_PRESSED_TIME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private var backPressedTime = 0L
    private val backPressedFlow = MutableSharedFlow<Unit>()

    override fun initView() {
        initMainBottomNavigation()
        initBackDoublePressed()
    }

    private fun initMainBottomNavigation() {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.fcv_home) as NavHostFragment)
                .findNavController()
        binding.bnvHome.setupWithNavController(navController)
        doubleBackPressedOnHomeTab(navController)
        setBottomNavigationVisibility(navController)
    }

    private fun doubleBackPressedOnHomeTab(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            onBackPressedCallback.isEnabled = destination.id == R.id.fragment_home
        }
    }

    private fun setBottomNavigationVisibility(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bnvHome.visibility = when (destination.id) {
                R.id.fragment_home, R.id.fragment_my_page, R.id.fragment_search -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    private fun initBackDoublePressed() {
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        observeBackPressedFlow()
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            lifecycleScope.launch {
                backPressedFlow.emit(Unit)
            }
        }
    }

    private fun observeBackPressedFlow() {
        lifecycleScope.launch {
            backPressedFlow.flowWithLifecycle(lifecycle).onEach {
                handleBackPressed()
            }.launchIn(lifecycleScope)
        }
    }

    private fun handleBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - backPressedTime <= TOTAL_PRESSED_TIME) {
            finish()
        } else {
            backPressedTime = currentTime
            snackBar(binding.root, getString(R.string.main_back_once_pressed_exit))
        }
    }
}
