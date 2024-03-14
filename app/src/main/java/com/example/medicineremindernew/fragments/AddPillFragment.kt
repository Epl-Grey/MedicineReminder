package com.example.medicineremindernew.fragments

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.medicineremindernew.DatabaseHelper
import com.example.medicineremindernew.R
import com.example.medicineremindernew.activities.AddTimeActivity
import com.example.medicineremindernew.activities.MainActivity
import com.example.medicineremindernew.models.Pill
import org.apache.commons.lang3.StringUtils
import java.io.Serializable
import java.util.Calendar

class AddPillFragment : Fragment() {
    private lateinit var selectItemOr: String
    private var countries = arrayOf<String?>("грамм", "мл", "стол. ложек", "табл")
    private lateinit var textAfter: TextView
    private lateinit var textBefore: TextView
    private lateinit var sqlHelper: DatabaseHelper
    private lateinit var db: SQLiteDatabase
    private lateinit var userCursor: Cursor
    private lateinit var nameEdit: EditText
    private lateinit var valueEdit: EditText
    var userId: Long = 0
    var times = arrayOf<String?>(
        "1 раз в день",
        "2 раза в день",
        "3 раза в день",
        "4 раза в день",
        "5 раз в день",
        "6 раз в день"
    )
    var datePickerDialog: DatePickerDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewP = inflater.inflate(R.layout.fragment_add_pill, container, false)

// присваение
        val spinner_num = viewP.findViewById<Spinner>(R.id.Spinner)
        val spinner_times = viewP.findViewById<Spinner>(R.id.spinnerTimes)
        textAfter = viewP.findViewById(R.id.textAfter)
        textBefore = viewP.findViewById(R.id.textBefore)
        nameEdit = viewP.findViewById(R.id.NameEdit)
        valueEdit = viewP.findViewById(R.id.NumberEdit)
        val btnChoose = viewP.findViewById<Button>(R.id.btnChoose)
        val main_activity_intent = Intent(context, MainActivity::class.java)


        //спинеры spinner_num, spinner_times
        val adapter: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), android.R.layout.simple_spinner_item, countries)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_num.adapter = adapter
        val adapter2: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireContext(), android.R.layout.simple_spinner_dropdown_item, times)
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_times.adapter = adapter2
        // вывод текста для выбара времени
        val intent = Intent(context, AddTimeActivity::class.java)
        textBefore.setOnClickListener {
            openDatePickerBefore(
                textBefore
            )
        }
        textAfter.setOnClickListener {
            openDatePickerAfter(
                textAfter
            )
        }
        btnChoose.setOnClickListener { view: View? ->
            if (nameEdit.getText().toString().isEmpty()) {
                nameEdit.setError("Enter name")
                return@setOnClickListener
            }
            if (valueEdit.getText().toString()
                    .isEmpty() || !StringUtils.isNumeric(valueEdit.text.toString())
            ) {
                valueEdit.setError("Enter value")
                return@setOnClickListener
            }

            val pill = Pill(
                name = nameEdit.text.toString().trim { it <= ' ' },
                dosage_value = valueEdit.text.toString().trim { it <= ' ' }.toInt(),
                dosage_unit = spinner_num.selectedItem.toString(),
                date_from = textBefore.text.toString().trim { it <= ' ' },
                date_to = textAfter.text.toString().trim { it <= ' ' }
            )

            intent.putExtra("item", spinner_times.selectedItem.toString())
            intent.putExtra("pill", pill as Serializable)

            startActivity(intent)
        }
        return viewP
    }

    var day2: String? = null
    var month2: String? = null
    private fun initDatePicker(textView: TextView?) {
        val dateSetListener = OnDateSetListener { datePicker, year, month, day ->
            var month = month
            month = month + 1
            day2 =
                if (day in 1..9) {
                    "0$day"
                } else {
                    day.toString()
                }
            month2 =
                if (month in 1..9) {
                    "0$month"
                } else {
                    month.toString()
                }
            val date = "$day2.$month2.$year"
            textView!!.text = date
        }
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]
        val style = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT
        datePickerDialog = DatePickerDialog(requireContext(), style, dateSetListener, year, month, day)
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    fun openDatePickerAfter(view: View?) {
        initDatePicker(textAfter)
        datePickerDialog!!.show()
    }

    fun openDatePickerBefore(view: View?) {
        initDatePicker(textBefore)
        datePickerDialog!!.show()
    }
}
