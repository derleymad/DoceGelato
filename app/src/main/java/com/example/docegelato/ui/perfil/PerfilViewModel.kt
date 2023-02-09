package com.example.docegelato.ui.perfil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.docegelato.model.pedidos.Pedidos

class PerfilViewModel : ViewModel() {
    private var _listPedidos = MutableLiveData<ArrayList<Pedidos>>()
    private var _listAbertos = MutableLiveData<ArrayList<Pedidos>>()
    private var _listAndamento = MutableLiveData<ArrayList<Pedidos>>()
    private var _listFechados = MutableLiveData<ArrayList<Pedidos>>()

    init {
        _listAbertos.value = ArrayList<Pedidos>()
    }

    var listPedidos = _listPedidos
    var listAbertos = _listAbertos
    var listAndamento = _listAndamento
    var listFechados = _listFechados


}