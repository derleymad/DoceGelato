package com.example.docegelato.util

import java.text.NumberFormat
import java.util.*

object Utils{
    fun format(number:Float?) : String?{
        return NumberFormat.getCurrencyInstance(Locale("pt-br", "br")).format(number)
    }

}