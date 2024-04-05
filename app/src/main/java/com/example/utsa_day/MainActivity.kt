package com.example.utsa_day

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.utsa_day.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()

        //add fade animation when splashScreen is ready to exit
        splashScreen.setOnExitAnimationListener { splash ->
            ObjectAnimator.ofFloat(splash.view, "alpha", 0f).apply {
                duration = 1000
                interpolator = AccelerateDecelerateInterpolator()
                this.doOnEnd {
                    splash.remove()
                }
                start()
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // create NavController for fragments
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_frag_container)
        val navController = navHostFragment!!.findNavController()
        val appBarConfiguration = AppBarConfiguration.Builder().build()
        setupWithNavController(binding.mainToolbar, navController, appBarConfiguration)
    }
}