package com.example.docegelato.util

import android.util.Log
import com.example.docegelato.model.categorias.Pedidos
import com.google.firebase.ktx.Firebase
import java.text.NumberFormat
import java.util.*

object Utils{
    fun format(number:Float?) : String?{
        return NumberFormat.getCurrencyInstance(Locale("pt-br", "br")).format(number)
    }

}