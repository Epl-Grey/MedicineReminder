package com.example.medicineremindernew.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.medicineremindernew.R
import com.example.medicineremindernew.SaveState
import com.example.medicineremindernew.firebase.UserData
import com.example.medicineremindernew.firebase.UsersManager
import com.example.medicineremindernew.services.AuthService
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.exceptions.BadRequestRestException
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var passwordRepeatEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var diabetesCheckbox: CheckBox
    private lateinit var saveState: SaveState

    @Inject
    lateinit var authService: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        loginEditText = findViewById(R.id.login_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        passwordRepeatEditText = findViewById(R.id.repeat_password_edit_text)
        submitButton = findViewById(R.id.submit_register)
        diabetesCheckbox = findViewById(R.id.diabetes_checkbox)

        submitButton.setOnClickListener {
            if(loginEditText.text.isEmpty()){
                loginEditText.error = "Enter login"
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
                        loginEditText.text.toString(),
                        passwordEditText.text.toString(),
                        loginEditText.text.toString(),
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
    }
}