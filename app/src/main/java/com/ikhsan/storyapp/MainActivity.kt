package com.ikhsan.storyapp

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ikhsan.storyapp.base.BaseActivity
import com.ikhsan.storyapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private var doubleBackToExitPressedOnce = false

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    override fun initView() {
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackPress()
            }
        })

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }
    }

    fun handleBackPress() {
        if (navController.currentDestination?.id == R.id.loginFragment || navController.currentDestination?.id == R.id.homeFragment) {
            if (doubleBackToExitPressedOnce) {
                finish()
            } else {
                onHandlerStart()
            }
        } else {
            navController.navigateUp()
        }
    }

    private fun onHandlerStart() {
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()

        lifecycleScope.launch {
            delay(2000)
            doubleBackToExitPressedOnce = false

        }
    }
}