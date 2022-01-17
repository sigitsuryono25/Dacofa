package com.surelabsid.lti.dacofa.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object HourToMillis {

    fun today(): String {
        return millisToDate(
            millis()
        )
    }

    fun millis(): Long {
        val time = Calendar.getInstance(Locale.ENGLISH)
        return time.timeInMillis
    }

    fun millisKurangBerapaHari(kurang: Int?): Long {
        val time = Calendar.getInstance(Locale.ENGLISH)
        kurang?.let { time.add(Calendar.DAY_OF_MONTH, it) }
        return time.timeInMillis
    }

    fun addExpired(expiredOn: Int?): Long {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val currentDateandTime = sdf.format(Date())

        val date = sdf.parse(currentDateandTime)
        val calendar = Calendar.getInstance()
        calendar.time = date!!
        expiredOn?.let { calendar.add(Calendar.HOUR, it) }
        println("Time here " + calendar.time)
        return calendar.timeInMillis
    }

    private fun millisToDate(millis: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val c = Calendar.getInstance()
        c.timeInMillis = millis
        return sdf.format(c.time)
    }

    fun millisToDateHour(millis: Long?): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy H:mm", Locale.getDefault())
        val c = Calendar.getInstance()
        c.timeInMillis = millis!!
        println("Time here " + sdf.format(c.time))
        return sdf.format(c.time)
    }

    fun millisToMonth(millis: Long): String {
        val sdf = SimpleDateFormat("MM", Locale.ENGLISH)
        val c = Calendar.getInstance()
        c.timeInMillis = millis
        return sdf.format(c.time)
    }

    fun millisToCustomFormat(millis: Long, format: String = "dd/MM/yyyy HH:mm"): String {
        val sdf = SimpleDateFormat(format, Locale.ENGLISH)
        val c = Calendar.getInstance()
        c.timeInMillis = millis
        return sdf.format(c.time)
    }

    fun dateDiff(oldDate: String, format: String = "yyyy-MM-dd HH:mm:ss"): Long {
        val dateFormat = SimpleDateFormat(format, Locale.getDefault())
        try {
            val old = dateFormat.parse(oldDate)
            val currentDate = Date()
            val diff = currentDate.time - old.time
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60

            return hours / 24

        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return 0
    }

}