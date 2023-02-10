package com.example.docegelato.ui.perfil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.docegelato.model.pedidos.Pedidos

class PerfilViewModel : ViewModel() {
    private var _listAbertos = MutableLiveData<ArrayList<Pedidos>>()
    private var _listAndamento = MutableLiveData<ArrayList<Pedidos>>()
    private var _listFinalizado = MutableLiveData<ArrayList<Pedidos>>()

    var listAbertos = _listAbertos
    var listAndamento = _listAndamento
    var listFinalizado = _listFinalizado

}