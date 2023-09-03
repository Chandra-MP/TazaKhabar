package com.example.tazakhabar.View

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.tazakhabar.R
//import com.example.tazakhabar.View.Hilt_MainActivity
import com.example.tazakhabar.LoginSignupActivity
import com.example.tazakhabar.splashFragment

class MainActivity : AppCompatActivity() {

    val handler = Handler()
    private val SPLASH_DURATION = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load the SplashFragment initially
        loadFragment(splashFragment())

        // Delayed navigation to MainMenuActivity
        handler.postDelayed({
            navigateToLoginSignupActivity()
        }, SPLASH_DURATION.toLong())
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    private fun navigateToLoginSignupActivity() {
        startActivity(Intent(this, LoginSignupActivity::class.java))
        finish()
    }


}
