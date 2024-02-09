package com.example.medicineremindernew.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.DatePicker
import android.widget.ListView
import android.widget.TextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.ListViewCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medicineremindernew.PillAdapter
import com.example.medicineremindernew.R
import com.example.medicineremindernew.activities.InformActivity
import com.example.medicineremindernew.alarm.AlarmController
import com.example.medicineremindernew.calendar.CalendarAdapter
import com.example.medicineremindernew.calendar.CalendarUtils
import com.example.medicineremindernew.databinding.FragmentHomeBinding
import com.example.medicineremindernew.services.PillsDataService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var calendar: TextView
    private lateinit var date: LocalDate
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var days: ArrayList<LocalDate>
    private lateinit var numberWeek: ArrayList<String>
    private lateinit var alarmController: AlarmController
    var positionG = 0

    @Inject
    lateinit var pillsDataService: PillsDataService

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewP = inflater.inflate(R.layout.fragment_home, container, false)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        CalendarUtils.selectedDate = LocalDate.now()
        setWeekView()
        date = CalendarUtils.selectedDate

        binding.minMonthBtn.setOnClickListener { previousWeekAction() }
        binding.plusMonthBtn.setOnClickListener { nextWeekAction() }
        binding.monthYearTextView.setOnClickListener { openDatePickerAfter(calendar) }

        alarmController = AlarmController(context)
        refreshPills()

        //TODO("Refresh pills")
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun refreshPills(){
        lifecycleScope.launch {
            val pills = withContext(Dispatchers.IO) {
                pillsDataService.getPills()
            }
            val listAdapter = PillAdapter(requireContext(), pills)
            binding.list.adapter = listAdapter
            binding.list.isClickable = true
            binding.list.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, i, l ->
                    val intent = Intent(requireContext(), InformActivity::class.java)
                    intent.putExtra("pill", pills[i] as java.io.Serializable)
                    startActivity(intent)
                }
        }




    }

    fun onCalendarItem(db: SQLiteDatabase?) {
        //TODO("Refresh pills")
    }

    private fun openDatePickerAfter(view: View?) {
        initDatePicker()
        datePickerDialog.show()
    }

    private fun setWeekView() {
        binding.monthYearTextView.text = CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate)
        days = CalendarUtils.daysInWeekArray(CalendarUtils.selectedDate)
        numberWeek = CalendarUtils.numberOfDays(CalendarUtils.selectedDate)
        println("numberWeek $numberWeek")
        val calendarAdapter = CalendarAdapter(
            { position: Int, date: LocalDate? -> onItemClick(position, date) },
            days,
            numberWeek,
            this
        )
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.calendarRecyclerView.layoutManager = linearLayoutManager
        binding.calendarRecyclerView.adapter = calendarAdapter
        binding.calendarRecyclerView.layoutManager!!.scrollToPosition(positionG - 3)
    }

    private fun previousWeekAction() {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1)
        setWeekView()
    }

    private fun nextWeekAction() {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1)
        setWeekView()
    }

    private fun onItemClick(position: Int, date: LocalDate?) {
        CalendarUtils.selectedDate = date
        println("positionpositionpositionposition $position")
        positionG = position
        setWeekView()
    }

    private fun initDatePicker() {
        val dateSetListener =
            OnDateSetListener { datePicker: DatePicker?, year: Int, month: Int, day: Int ->
                var month = month
                month = month + 1
                date = LocalDate.of(year, month, day)
                onItemClick(1, date)
            }
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]
        val style = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT
        datePickerDialog = DatePickerDialog(requireContext(), style, dateSetListener, year, month, day)
    }

    private fun openDatePickerAfter() {
        initDatePicker()
        datePickerDialog.show()
    }
}