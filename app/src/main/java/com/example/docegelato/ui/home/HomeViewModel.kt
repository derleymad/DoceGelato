package com.example.docegelato.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.docegelato.model.Comidas

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply { value = "This is home Fragment" }
    private val _comidas = MutableLiveData<List<Comidas>>()

    val text: LiveData<String> = _text
    val comidas : LiveData<List<Comidas>> = _comidas

    init {
        addItem()
    }

    fun addItem(){
        _comidas.value = mutableListOf(Comidas("primeiro","segundo"))
    }
}