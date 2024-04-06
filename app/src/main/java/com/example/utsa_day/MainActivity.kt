package com.example.utsa_day

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.utsa_day.databinding.ActivityMainBinding
import com.example.utsa_day.leaderboard.model.LeaderboardDatabase
import java.util.Locale


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels { getViewModelFactory() }

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

        mainViewModel.changeLanguage.observe(this) {
            setLocale()
        }
        // create NavController for fragments
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_frag_container) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.startupFragment
        ).build()
        with(binding.mainToolbar) {
            setNavigationOnClickListener { navController.navigateUp() }
            setupWithNavController(navController, appBarConfiguration)
        }
    }

    private fun getViewModelFactory(): MainViewModelFactory {
        val dataSource = LeaderboardDatabase.getInstance(this).profileImageDao

        return MainViewModelFactory(dataSource)
    }

    fun setLocale() {
        var languageCode = "es"
        val current = getResources().configuration.getLocales().get(0)
        if (current == Locale("es")) {
            languageCode = "en"
        }
        val config = resources.configuration
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        config.setLocale(locale)

        createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)

        val intent = intent
        finish()
        startActivity(intent)
    }
}