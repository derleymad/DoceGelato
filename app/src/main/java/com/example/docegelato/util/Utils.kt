package com.example.docegelato.util

import android.text.format.DateUtils
import android.text.format.DateUtils.getRelativeTimeSpanString
import androidx.fragment.app.Fragment
import java.text.DateFormat
import java.text.NumberFormat
import java.util.*

object Utils{
    fun format(number:Float?) : String?{
        return NumberFormat.getCurrencyInstance(Locale("pt-br", "br")).format(number)
    }
    fun formatDate(date : Long?) : String?{
        val niceString =  DateUtils.getRelativeTimeSpanString(date!!,Calendar.getInstance().timeInMillis, DateUtils.MINUTE_IN_MILLIS).toString()
        val obj: DateFormat = DateFormat.getDateTimeInstance()
        val res = obj.format(date!!)
        return niceString
    }

}