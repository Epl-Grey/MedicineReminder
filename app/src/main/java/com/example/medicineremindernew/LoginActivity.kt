package com.example.medicineremindernew

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

    lateinit var saveState: SaveState;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginEditText = findViewById(R.id.login_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        submitButton = findViewById(R.id.submit_login)
        submitButton2 = findViewById(R.id.register)

        saveState = SaveState(this, "ob")
        val intent2= Intent(this, MainActivity::class.java)
        if (saveState.state >= 2) {
            startActivity(intent2)
            finish()
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

            val userManager = UsersManager()

            userManager.listener = fun(it: ArrayList<UserData>) {
                for (user in it) {
                    System.out.println("${loginEditText.text}   ${user.userLogin!!} = ${user.userLogin!!.equals(loginEditText.text.toString())}")
                    if (user.userLogin!!.equals(loginEditText.text.toString())) {
                        System.out.println("USER FOUNDED")
                        val passwordHash: String = MessageDigest.getInstance("SHA-512")
                            .digest(passwordEditText.text.toString().toByteArray())
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
                        }
                    }
                }
                Toast.makeText(this, "Invalid auth data", Toast.LENGTH_SHORT).show()
            }

            saveState.state = 2
            startActivity(intent)
            finish()
        }


        submitButton2.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }
}