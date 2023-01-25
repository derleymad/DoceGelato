package com.example.docegelato.model.categorias

data class Pedidos(
    val pedidos: MutableList<Pedido>? = null,
    var address: Address? = null,
    var user: User? = null,
    var preco_total: Float? = 0f
){

}


