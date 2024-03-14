package com.example.medicineremindernew.fragments

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.medicineremindernew.R
import com.example.medicineremindernew.SaveSound
import com.example.medicineremindernew.SaveState
import com.example.medicineremindernew.activities.LoginActivity
import com.example.medicineremindernew.services.AuthService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var voiceMelodyBtn: Button
    private lateinit var themeBtn: Button
    private lateinit var saveSound: SaveSound
    private lateinit var singOutBtn: Button
    private val isDarkTheme = false
    private lateinit var saveState: SaveState

    @Inject
    lateinit var authService: AuthService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewP = inflater.inflate(R.layout.fragment_settings, container, false)
        saveSound = SaveSound(context, "sound")
        saveState = SaveState(context, "ob")

        themeBtn = viewP.findViewById(R.id.ThemeBtn)
        voiceMelodyBtn = viewP.findViewById(R.id.voiceMelodyBtn)
        singOutBtn = viewP.findViewById(R.id.singOutBtn)

        voiceMelodyBtn.setOnClickListener{ openFilePicker() }
        themeBtn.setOnClickListener{ toggleTheme() }
        singOutBtn.setOnClickListener {
            val loginIntent = Intent(context, LoginActivity::class.java)
            GlobalScope.launch {
                authService.signOut()
                saveState.state = 1
                startActivity(loginIntent)
            }
        }
        return viewP
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("audio/mpeg") // Указывает mp3 тип файлов
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.data != null) {
                val selectedFileUri = data.data
                println("uri $selectedFileUri")
                saveSound.state = selectedFileUri.toString()
            }
        }
    }

    private fun toggleTheme() {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        requireActivity().recreate()
    }

    companion object {
        private const val REQUEST_CODE = 1
    }
}