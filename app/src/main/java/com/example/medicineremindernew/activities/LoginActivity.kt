package com.example.medicineremindernew.activities

import com.example.medicineremindernew.R
import com.example.medicineremindernew.SaveState

import android.content.Intent
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.medicineremindernew.firebase.UserData
import com.example.medicineremindernew.firebase.UsersManager
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {
    lateinit var loginEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var submitButton: Button
    lateinit var submitButton2: TextView

    lateinit var saveState: SaveState
    lateinit var mainIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginEditText = findViewById(R.id.login_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        submitButton = findViewById(R.id.submit_login)
        submitButton2 = findViewById(R.id.register)

        saveState = SaveState(this, "ob")

        mainIntent = Intent(this, MainActivity::class.java)

        submitButton.setOnClickListener {
            if(loginEditText.text.isEmpty()){
                loginEditText.error = "Enter login"
                return@setOnClickListener
            }
            if(passwordEditText.text.isEmpty()){
                passwordEditText.error = "Enter password"
                return@setOnClickListener
            }

            val userManager = UsersManager()

            userManager.listener = fun(it: ArrayList<UserData>) {
                for (user in it) {
                    System.out.println("${loginEditText.text}   ${user.userLogin!!} = ${user.userLogin!!.equals(loginEditText.text.toString())}")
                    if (user.userLogin!!.equals(loginEditText.text.toString().trim())) {
                        System.out.println("USER FOUNDED")
                        val passwordHash: String = MessageDigest.getInstance("SHA-512")
                            .digest(passwordEditText.text.toString().trim().toByteArray())
                            .joinToString(separator = "") {
                                ((it.toInt() and 0xff) + 0x100)
                                    .toString(16)
                                    .substring(1)
                            }

                        if (user.userPassword!! == passwordHash) {
                            Toast.makeText(this@LoginActivity, "Login succeed!", Toast.LENGTH_LONG).show()
                            val editor = getSharedPreferences("UserInfo", Context.MODE_PRIVATE).edit()
                            editor.putString("userName", user.userLogin)
                            editor.apply()

                            saveState.state = 2
                            startActivity(mainIntent)
                            finish()
                        }
                    }
                }
                Toast.makeText(this, "Invalid auth data", Toast.LENGTH_SHORT).show()
            }
        }


        submitButton2.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }


    }
}