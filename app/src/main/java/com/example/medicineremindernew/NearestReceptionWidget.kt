package com.example.medicineremindernew

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import androidx.lifecycle.lifecycleScope
import com.example.medicineremindernew.models.Pill
import com.example.medicineremindernew.services.PillsDataService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class NearestReceptionWidget : AppWidgetProvider() {
        @Inject
    lateinit var pillsDataService: PillsDataService
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        GlobalScope.launch {
            val pills = withContext(Dispatchers.IO) {
                pillsDataService.getPills() }
            var nearestPill: Pill? = null
            var nearestTime: String? = null
            var nearestDeltaTime: Long = Long.MAX_VALUE

            val now = Calendar.getInstance()



            for(pill in pills){
                var tempTime: String? = null
                var tempDeltaTime: Long = Long.MAX_VALUE
                for(time in pill.times!!){
                    if(getMillis(time) > now.timeInMillis && getMillis(time) - now.timeInMillis < tempDeltaTime){
                        tempDeltaTime = getMillis(time) - now.timeInMillis
                        tempTime = time
                    }
                }
                if(tempDeltaTime < nearestDeltaTime){
                    nearestPill = pill
                    nearestTime = tempTime
                    nearestDeltaTime = tempDeltaTime
                }
            }
            val map = mapOf("name" to nearestPill!!.name, "time" to nearestTime!!)

            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId, map["name"].toString(), map["time"].toString())
            }
        }


    }
        private fun getMillis(time: String): Long{
        val timeList = time.split(":")
        val tempCalendar = Calendar.getInstance()
        tempCalendar[Calendar.MINUTE] = timeList[1].toInt()
        tempCalendar[Calendar.HOUR_OF_DAY] = timeList[0].toInt()

        return tempCalendar.timeInMillis
    }
    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    name: String,
    time: String
) {
    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.nearest_reception_widget)
    views.setTextViewText(R.id.name2, name)
    views.setTextViewText(R.id.time2, time)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
