package com.example.docegelato.ui.carrinho

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.docegelato.model.pedidos.Pedido

class PedidosViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {}
    private val _listaDePedidos = MutableLiveData<MutableList<Pedido>>()

    val text: LiveData<String> = _text
    val listaDePedidos: LiveData<MutableList<Pedido>> = _listaDePedidos
}