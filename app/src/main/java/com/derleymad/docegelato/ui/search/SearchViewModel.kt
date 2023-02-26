package com.derleymad.docegelato.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    private val _outro = MutableLiveData<String>().apply {
        value = "Outro"
    }
    val text: LiveData<String> = _text
}