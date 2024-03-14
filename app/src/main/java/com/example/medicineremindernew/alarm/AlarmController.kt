package com.example.medicineremindernew.alarm

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.example.medicineremindernew.DatabaseHelper
import com.example.medicineremindernew.activities.MainActivity
import com.example.medicineremindernew.models.Pill
import com.example.medicineremindernew.services.PillsDataService
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

class AlarmController @Inject constructor(
    @ApplicationContext private val context: Context,
    private val pillsDataService: PillsDataService
) {

    suspend fun refresh(pills: List<Pill>) {
        pills.forEach { pill ->
            pill.times!!.forEach { time ->
                addAlarm(pill.name!!, pill.date_from!!, pill.date_to!!, time)
                addAlarmNotify(pill.name!!, pill.date_from!!, pill.date_to!!, time)
            }
        }
    }

    fun addAlarm(name: String, date1: String, date2: String, time: String) {
        if (time == "" || date1 == "дата" || date2 == "дата") return
        val time = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val date1 = date1.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val date2 = date2.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

//        System.out.println("\n\n===ALARMS===");
        val calendarDate1 = Calendar.getInstance()
        calendarDate1[Calendar.MILLISECOND] = 0
        calendarDate1[Calendar.SECOND] = 0
        calendarDate1[Calendar.MINUTE] = time[1].toInt()
        calendarDate1[Calendar.HOUR_OF_DAY] = time[0].toInt()
        calendarDate1[Calendar.DAY_OF_MONTH] = date1[0].toInt()
        calendarDate1[Calendar.MONTH] = date1[1].toInt() - 1
        calendarDate1[Calendar.YEAR] = date1[2].toInt()
        val calendarDate2 = Calendar.getInstance()
        calendarDate2[Calendar.MILLISECOND] = 0
        calendarDate2[Calendar.SECOND] = 0
        calendarDate2[Calendar.MINUTE] = time[1].toInt()
        calendarDate2[Calendar.HOUR_OF_DAY] = time[0].toInt()
        calendarDate2[Calendar.DAY_OF_MONTH] = date2[0].toInt()
        calendarDate2[Calendar.MONTH] = date2[1].toInt() - 1
        calendarDate2[Calendar.YEAR] = date2[2].toInt()
        val alarmCalendar = Calendar.getInstance()
        alarmCalendar[Calendar.MILLISECOND] = 0
        alarmCalendar[Calendar.SECOND] = 0
        alarmCalendar[Calendar.MINUTE] = time[1].toInt()
        alarmCalendar[Calendar.HOUR_OF_DAY] = time[0].toInt()

//        System.out.println("Date1  " + calendar_date1.get(Calendar.DAY_OF_MONTH) + "." + calendar_date1.get(Calendar.MONTH) + "." + calendar_date1.get(Calendar.YEAR));
//        System.out.println("Date2  " + calendar_date2.get(Calendar.DAY_OF_MONTH) + "." + calendar_date2.get(Calendar.MONTH) + "." + calendar_date2.get(Calendar.YEAR));

//        System.out.println(alarm_calendar.getTime() + " > " + calendar_date1.getTime() + " = " + (alarm_calendar.compareTo(calendar_date1) > 0));
//        System.out.println(alarm_calendar.getTime() + " > " + calendar_date2.getTime() + " = " + (alarm_calendar.compareTo(calendar_date2) < 0));
        if ((alarmCalendar > calendarDate1) and (alarmCalendar < calendarDate2)) {
//            System.out.println(Calendar.getInstance().getTime() + " > " + alarm_calendar.getTime() + " = " + (Calendar.getInstance().compareTo(alarm_calendar) > 0));
            if (Calendar.getInstance() > alarmCalendar) {
                alarmCalendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val alarmClockInfo = AlarmClockInfo(alarmCalendar.timeInMillis, alarmInfoPendingIntent)
            try {
                alarmManager.setAlarmClock(alarmClockInfo, getAlarmActionPendingIntent(name))
            } catch (e: SecurityException) {
                Log.e("alarm", "alarmManager.setAlarmClock SecureException")
            }
        }
    }

    private val alarmInfoPendingIntent: PendingIntent
        get() {
            val alarmInfoIntent = Intent(context, MainActivity::class.java)
            alarmInfoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getActivity(
                    context,
                    0,
                    alarmInfoIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
                )
            } else {
                PendingIntent.getActivity(
                    context,
                    0,
                    alarmInfoIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
        }

    private fun getAlarmActionPendingIntent(name: String): PendingIntent {
        val intent = Intent(context, AlarmActivity::class.java)
        intent.putExtra("name", name)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                context,
                name.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
            )
        } else {
            PendingIntent.getActivity(
                context,
                name.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }

    private fun addAlarmNotify(name: String, date1: String, date2: String, time: String) {
        if (time == "" || date1 == "дата" || date2 == "дата") return
        val time = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val date1 = date1.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val date2 = date2.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

//        System.out.println("\n\n===NOFICATIONS===");
//
//        System.out.println("Date1: " + Arrays.toString(date1));
//        System.out.println("Date2: " + Arrays.toString(date2));
        val calendarDate1 = Calendar.getInstance()
        calendarDate1[Calendar.MILLISECOND] = 0
        calendarDate1[Calendar.SECOND] = 0
        calendarDate1[Calendar.MINUTE] = time[1].toInt()
        calendarDate1[Calendar.HOUR_OF_DAY] = time[0].toInt()
        calendarDate1[Calendar.DAY_OF_MONTH] = date1[0].toInt()
        calendarDate1[Calendar.MONTH] = date1[1].toInt() - 1
        calendarDate1[Calendar.YEAR] = date1[2].toInt()
        val calendarDate2 = Calendar.getInstance()
        calendarDate2[Calendar.MILLISECOND] = 0
        calendarDate2[Calendar.SECOND] = 0
        calendarDate2[Calendar.MINUTE] = time[1].toInt()
        calendarDate2[Calendar.HOUR_OF_DAY] = time[0].toInt()
        calendarDate2[Calendar.DAY_OF_MONTH] = date2[0].toInt()
        calendarDate2[Calendar.MONTH] = date2[1].toInt() - 1
        calendarDate2[Calendar.YEAR] = date2[2].toInt()
        val alarmCalendar = Calendar.getInstance()
        alarmCalendar[Calendar.MILLISECOND] = 0
        alarmCalendar[Calendar.SECOND] = 0
        alarmCalendar[Calendar.MINUTE] = time[1].toInt()
        alarmCalendar[Calendar.HOUR_OF_DAY] = time[0].toInt()

//        System.out.println("Date1  " + calendar_date1.get(Calendar.DAY_OF_MONTH) + "." + calendar_date1.get(Calendar.MONTH) + "." + calendar_date1.get(Calendar.YEAR));
//        System.out.println("Date2  " + calendar_date2.get(Calendar.DAY_OF_MONTH) + "." + calendar_date2.get(Calendar.MONTH) + "." + calendar_date2.get(Calendar.YEAR));
//
//        System.out.println(alarm_calendar.getTime() + " > " + calendar_date1.getTime() + " = " + (alarm_calendar.compareTo(calendar_date1) > 0));
//        System.out.println(alarm_calendar.getTime() + " > " + calendar_date2.getTime() + " = " + (alarm_calendar.compareTo(calendar_date2) < 0));
        if ((alarmCalendar > calendarDate1) and (alarmCalendar < calendarDate2)) {
//            System.out.println(Calendar.getInstance().getTime() + " > " + alarm_calendar.getTime() + " = " + (Calendar.getInstance().compareTo(alarm_calendar) > 0));
            if (Calendar.getInstance().compareTo(alarmCalendar) > 0) {
                alarmCalendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            val intent = Intent(context, AlarmReceiverNotify::class.java)
            intent.putExtra("name", name)
            val df = SimpleDateFormat("HH:mm")
            intent.putExtra("time", df.format(alarmCalendar.time))
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                UUID.randomUUID()!!.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            alarmCalendar.add(Calendar.MINUTE, -15)
            //            alarm_calendar.add(Calendar.DAY_OF_MONTH, +1);
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

//            System.out.println(pill.name + " " + alarm_calendar.getTime());

//            System.out.println("setAlarmNotify");
            println(name + " " + alarmCalendar.time)
            try {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    alarmCalendar.timeInMillis,
                    pendingIntent
                )
            } catch (e: SecurityException) {
                Log.e("alarm", "alarmManager.setExactAndAllowWhileIdle SecureException")
            }
            println("setAlarmNotify")
        }
    }
}
