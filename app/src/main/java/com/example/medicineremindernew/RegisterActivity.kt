package com.example.medicineremindernew

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.medicineremindernew.firebase.UserData
import com.example.medicineremindernew.firebase.UsersManager

class RegisterActivity : AppCompatActivity() {
    lateinit var loginEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var passwordRepeatEditText: EditText
    lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        loginEditText = findViewById(R.id.login_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        passwordRepeatEditText = findViewById(R.id.repeat_password_edit_text)
        submitButton = findViewById(R.id.submit_register)

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
            if(!passwordEditText.text.toString().equals(passwordRepeatEditText.text.toString())){
                Toast.makeText(this@RegisterActivity, "Passwords are different", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val userManager = UsersManager()

            userManager.listener = fun(it: ArrayList<UserData>) {
                for (user in userManager.users) {
                    if (user.userLogin!!.toString().equals(loginEditText.text.toString())) {
                        Toast.makeText(this@RegisterActivity, "Login already in use", Toast.LENGTH_LONG).show()
                        return
                    }
                }
                userManager.saveData(loginEditText.text.toString(), passwordEditText.text.toString())
                Toast.makeText(this@RegisterActivity, "Registration succeed!\nHave a good day", Toast.LENGTH_LONG).show()
                val editor = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE).edit()
                editor.putString("userId", passwordEditText.text.toString())
                editor.apply()
            }
        }
    }
}