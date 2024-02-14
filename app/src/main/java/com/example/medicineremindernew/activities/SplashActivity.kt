package com.example.medicineremindernew.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.medicineremindernew.R
import com.example.medicineremindernew.services.AuthService
import com.example.medicineremindernew.services.PillsDataService
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        val decorView = window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions

//        val mainIntent = Intent(this, MainActivity::class.java)
//
//        GlobalScope.launch {
//            authService.supabase.auth.sessionStatus.collect {
//                when(it) {
//                    is SessionStatus.Authenticated, SessionStatus.NotAuthenticated -> {
//                        Log.d("login", "SplashActivity: Authenticated")
//                        startActivity(mainIntent)
//                        finish()
//                    }
//                    SessionStatus.LoadingFromStorage -> Log.d("login", "SplashActivity: Loading from storage")
//                    SessionStatus.NetworkError -> Log.d("login", "SplashActivity: Network error")
//                }
//            }
//        }

        Handler().postDelayed({
            val i = Intent(this@SplashActivity, StartOnBoardingScreenActivity::class.java)
            startActivity(i)
            finish()
        }, 1500)
    }
}
