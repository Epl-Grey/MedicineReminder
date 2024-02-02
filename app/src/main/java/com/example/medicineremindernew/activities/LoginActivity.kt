package com.example.medicineremindernew.activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.medicineremindernew.R
import com.example.medicineremindernew.services.AuthService
import com.example.medicineremindernew.services.PillsDataService
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.exceptions.BadRequestRestException
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    lateinit var loginEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var submitButton: Button
    lateinit var submitButton2: TextView

    @Inject
    lateinit var  authService: AuthService
    @Inject
    lateinit var pillsDataService: PillsDataService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginEditText = findViewById(R.id.login_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        submitButton = findViewById(R.id.submit_login)
        submitButton2 = findViewById(R.id.register)

        val mainIntent = Intent(this, MainActivity::class.java)

        GlobalScope.launch {
            authService.supabase.auth.sessionStatus.collect {
                when(it) {
                    is SessionStatus.Authenticated -> {
                        startActivity(mainIntent)
                        finish()
                    }
                    SessionStatus.LoadingFromStorage -> Log.d("login", "Loading from storage")
                    SessionStatus.NetworkError -> Log.d("login", "Network error")
                    SessionStatus.NotAuthenticated -> Log.d("login", "Not authenticated")
                }
            }
        }


        submitButton.setOnClickListener {
            if(loginEditText.text.isEmpty()){
                loginEditText.error = "Enter login"
                return@setOnClickListener
            }
            if(passwordEditText.text.isEmpty()){
                passwordEditText.error = "Enter password"
                return@setOnClickListener
            }

            try {
                runBlocking {
                    Log.d("login", authService.isSignedIn().toString())
                    authService.signIn(loginEditText.text.toString(), passwordEditText.text.toString())
                }

                Toast.makeText(this@LoginActivity, "Login succeed!", Toast.LENGTH_SHORT).show()
                val homeIntent = Intent(this, MainActivity::class.java)
                startActivity(homeIntent)
                finish()

            }catch (e: BadRequestRestException){
                Toast.makeText(this@LoginActivity, e.error, Toast.LENGTH_LONG).show()
                Log.e("login", e.error)
            }
        }




        passwordEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                submitButton.performClick()
                return@OnKeyListener true
            }
            false
        })

        submitButton2.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }


    }
}