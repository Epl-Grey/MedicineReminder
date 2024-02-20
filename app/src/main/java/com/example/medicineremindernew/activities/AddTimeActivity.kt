package com.example.medicineremindernew.activities

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.medicineremindernew.R
import com.example.medicineremindernew.alarm.AlarmController
import com.example.medicineremindernew.models.Pill
import com.example.medicineremindernew.services.PillsDataService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class AddTimeActivity : AppCompatActivity() {
    private lateinit var setTimes1: TextView
    private lateinit var setTimes2: TextView
    private lateinit var setTimes3: TextView
    private lateinit var setTimes4: TextView
    private lateinit var setTimes5: TextView
    private lateinit var setTimes6: TextView
    private lateinit var nextBtn: Button
    private var dateAndTime: Calendar = Calendar.getInstance()
    var userId: Long = 0
    var hour = 0
    var minute = 0
    var times = arrayOf(
        "1 раз в день",
        "2 раза в день",
        "3 раза в день",
        "4 раза в день",
        "5 раз в день",
        "6 раз в день"
    )
    var selectItem: String? = null

    @Inject
    lateinit var pillsDataService: PillsDataService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_time)
        setTimes1 = findViewById(R.id.setTimes)
        setTimes2 = findViewById(R.id.setTimes2)
        setTimes3 = findViewById(R.id.setTimes3)
        setTimes4 = findViewById(R.id.setTimes4)
        setTimes5 = findViewById(R.id.setTimes5)
        setTimes6 = findViewById(R.id.setTimes6)
        nextBtn = findViewById(R.id.nextBtn)
        userId = intent.getLongExtra("id", 0)

        //sqlHelper = DatabaseHelper(this)
        //db = sqlHelper!!.writableDatabase
        selectItem = intent.getStringExtra("item")

        //Toast.makeText(
        //    applicationContext,
        //    selectItem, Toast.LENGTH_SHORT
        //).show()


        if (selectItem == times[0]) {
            setTimes1.visibility = View.VISIBLE
            setTimes2.visibility = View.GONE
            setTimes3.visibility = View.GONE
            setTimes4.visibility = View.GONE
            setTimes5.visibility = View.GONE
            setTimes6.visibility = View.GONE
        } else if (selectItem == times[1]) {
            setTimes1.visibility = View.VISIBLE
            setTimes2.visibility = View.VISIBLE
            setTimes3.visibility = View.GONE
            setTimes4.visibility = View.GONE
            setTimes5.visibility = View.GONE
            setTimes6.visibility = View.GONE
        } else if (selectItem == times[2]) {
            setTimes1.visibility = View.VISIBLE
            setTimes2.visibility = View.VISIBLE
            setTimes3.visibility = View.VISIBLE
            setTimes4.visibility = View.GONE
            setTimes5.visibility = View.GONE
            setTimes6.visibility = View.GONE
        } else if (selectItem == times[3]) {
            setTimes1.visibility = View.VISIBLE
            setTimes2.visibility = View.VISIBLE
            setTimes3.visibility = View.VISIBLE
            setTimes4.visibility = View.VISIBLE
            setTimes5.visibility = View.GONE
            setTimes6.visibility = View.GONE
        } else if (selectItem == times[4]) {
            setTimes1.visibility = View.VISIBLE
            setTimes2.visibility = View.VISIBLE
            setTimes3.visibility = View.VISIBLE
            setTimes4.visibility = View.VISIBLE
            setTimes5.visibility = View.VISIBLE
            setTimes6.visibility = View.GONE
        } else if (selectItem == times[5]) {
            setTimes1.visibility = View.VISIBLE
            setTimes2.visibility = View.VISIBLE
            setTimes3.visibility = View.VISIBLE
            setTimes4.visibility = View.VISIBLE
            setTimes5.visibility = View.VISIBLE
            setTimes6.visibility = View.VISIBLE
        }
        setTimes1.setOnClickListener { setTimePicker(setTimes1) }
        setTimes2.setOnClickListener { setTimePicker(setTimes2) }
        setTimes3.setOnClickListener { setTimePicker(setTimes3) }
        setTimes4.setOnClickListener { setTimePicker(setTimes4) }
        setTimes5.setOnClickListener { setTimePicker(setTimes5) }
        setTimes6.setOnClickListener { setTimePicker(setTimes6) }
    }

    fun save(view: View) {
        if (setTimes1.visibility == View.VISIBLE && setTimes1.text.toString().isEmpty()) {
            setTimes1.error = "Choose time"
            return
        }
        if (setTimes2.visibility == View.VISIBLE && setTimes2.text.toString().isEmpty()) {
            setTimes2.error = "Choose time"
            return
        }
        if (setTimes3.visibility == View.VISIBLE && setTimes3.text.toString().isEmpty()) {
            setTimes3.error = "Choose time"
            return
        }
        if (setTimes4.visibility == View.VISIBLE && setTimes4.text.toString().isEmpty()) {
            setTimes4.error = "Choose time"
            return
        }
        if (setTimes5.visibility == View.VISIBLE && setTimes5.text.toString().isEmpty()) {
            setTimes5.error = "Choose time"
            return
        }
        if (setTimes6.visibility == View.VISIBLE && setTimes6.text.toString().isEmpty()) {
            setTimes6.error = "Choose time"
            return
        }

//        val sharedPreference = getSharedPreferences("UserInfo", MODE_PRIVATE)
//        val userId = sharedPreference.getString("userName", "userId don't set")
//        val pillsManager = PillsManager(this)
//        pillsManager.saveData(
//            userId!!,
//            intent.getStringExtra("name")!!,
//            intent.getStringExtra("value")!!,
//            intent.getStringExtra("dos")!!,
//            intent.getStringExtra("data1")!!,
//            intent.getStringExtra("data2")!!,
//            setTimes1.text.toString(),
//            setTimes2.text.toString(),
//            setTimes3.text.toString(),
//            setTimes4.text.toString(),
//            setTimes5.text.toString(),
//            setTimes6.text.toString(),
//            intent.getStringExtra("Item")!!
//        )

        val pill = intent.getSerializableExtra("pill") as Pill

        pill.times = listOf(setTimes1, setTimes2, setTimes3, setTimes4, setTimes5, setTimes6).map{
            it.text.toString()
        }.filter {
            it != ""
        }

        GlobalScope.launch {
            pillsDataService.createPill(pill)
        }

        goToMainRes()
    }

    // установка начальных даты и времени
    private fun setInitialDateTime(textView: TextView) {
        textView.text = DateUtils.formatDateTime(
            this,
            dateAndTime.timeInMillis, DateUtils.FORMAT_SHOW_TIME
        )
    }

    // метод для выбора времени
    fun setTimePicker(textView: TextView?) {
        val onTimeSetListener = OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
            hour = selectedHour
            minute = selectedMinute
            textView!!.text = String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
        }
        val style = AlertDialog.THEME_HOLO_LIGHT
        val timePickerDialog = TimePickerDialog(this, style, onTimeSetListener, hour, minute, true)
        timePickerDialog.setTitle("До какого времени")
        timePickerDialog.show()
    }

    private fun goToMainRes() {
            // закрываем подключение
            // переход к главной activity
            val intent = Intent(this, AddGoodActivity::class.java)
            val addgood = "1"
            intent.putExtra("addgood", addgood)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
        }
}