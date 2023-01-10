package com.example.docegelato.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    private val _outro = MutableLiveData<String>().apply {
        value = "Outro"
    }
    val text: LiveData<String> = _text
    val outro: LiveData<String> =  _outro
}