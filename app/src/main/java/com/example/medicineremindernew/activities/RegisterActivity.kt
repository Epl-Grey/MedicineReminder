package com.example.medicineremindernew.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.medicineremindernew.R
import com.example.medicineremindernew.services.AuthService
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.exceptions.BadRequestRestException
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordRepeatEditText: EditText
    private lateinit var submitButton: Button

    @Inject
    lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nameEditText = findViewById(R.id.name_edit_text)
        emailEditText = findViewById(R.id.email_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        passwordRepeatEditText = findViewById(R.id.repeat_password_edit_text)
        submitButton = findViewById(R.id.submit_register)


        submitButton.setOnClickListener {
            if(emailEditText.text.isEmpty()){
                emailEditText.error = "Enter login"
                return@setOnClickListener
            }
            if(passwordEditText.text.isEmpty()){
                passwordEditText.error = "Enter password"
                return@setOnClickListener
            }
            if(passwordRepeatEditText.text.isEmpty()){
                passwordRepeatEditText.error = "Repeat password"
                return@setOnClickListener
            }
            if(passwordEditText.text.toString().trim() != passwordRepeatEditText.text.toString().trim()){
                Toast.makeText(this@RegisterActivity, "Passwords are different", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            try {
                runBlocking {
                    authService.createUser(
                        emailEditText.text.toString(),
                        passwordEditText.text.toString(),
                        emailEditText.text.toString(),
                    )
                }

                Toast.makeText(this@RegisterActivity, "Registration succeed!", Toast.LENGTH_SHORT).show()
                val homeIntent = Intent(this, MainActivity::class.java)
                startActivity(homeIntent)
                finish()

            }catch (e: BadRequestRestException){
                Toast.makeText(this@RegisterActivity, e.error, Toast.LENGTH_LONG).show()
                Log.e("register", e.error)
            }
        }
        passwordRepeatEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                submitButton.performClick()
                return@OnKeyListener true
            }
            false
        })

    }
}