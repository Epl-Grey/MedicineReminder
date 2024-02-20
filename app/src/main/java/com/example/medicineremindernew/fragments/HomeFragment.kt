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
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.medicineremindernew.DatabaseHelper
import com.example.medicineremindernew.PillAdapter
import com.example.medicineremindernew.PillsView
import com.example.medicineremindernew.activities.InformActivity
import com.example.medicineremindernew.alarm.AlarmController
import com.example.medicineremindernew.calendar.CalendarAdapter
import com.example.medicineremindernew.calendar.CalendarUtils
import com.example.medicineremindernew.calendar.CalendarUtils.selectedDate
import com.example.medicineremindernew.databinding.FragmentHomeBinding
import com.example.medicineremindernew.models.Pill
import com.example.medicineremindernew.services.PillsDataService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var date: LocalDate
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var days: ArrayList<LocalDate>
    private lateinit var numberWeek: ArrayList<String>
    private lateinit var pills: List<Pill>
    private var positionG = 0

    @Inject
    lateinit var pillsDataService: PillsDataService

    @Inject
    lateinit var alarmController: AlarmController

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        CalendarUtils.selectedDate = LocalDate.now()
        setWeekView()
        date = CalendarUtils.selectedDate

        binding.minMonthBtn.setOnClickListener { previousWeekAction() }
        binding.plusMonthBtn.setOnClickListener { nextWeekAction() }
        binding.monthYearTextView.setOnClickListener { openDatePickerAfter(binding.monthYearTextView) }

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
            pills = withContext(Dispatchers.IO) {
                pillsDataService.getPills()
            }.filter {
                val str = it.date_from!!.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                val str2 = it.date_to!!.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()

                val dataSplit = str[2] + "-" + str[1] + "-" + str[0]
                val localDate = LocalDate.parse(dataSplit)
                println("str $localDate")

                val dateSplit2 = str2[2] + "-" + str2[1] + "-" + str2[0]
                val localDate2 = LocalDate.parse(dateSplit2)
                println("str2 $localDate2")

                selectedDate.isAfter(localDate) && selectedDate.isBefore(localDate2) || selectedDate == localDate || selectedDate == localDate2
            }
            val listAdapter = PillAdapter(requireContext(), pills)
            binding.list.adapter = listAdapter
            binding.list.isClickable = true
            binding.list.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, i, l ->
                    val intent = Intent(requireContext(), InformActivity::class.java)
                    intent.putExtra("pill", pills[i] as Serializable)
                    startActivity(intent)
                }

            alarmController.refresh(pills)
        }
    }

    fun onCalendarItem(db: SQLiteDatabase?) {
        refreshPills()
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

        refreshPills()
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
        refreshPills()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("pills", pills as Serializable)
    }

}