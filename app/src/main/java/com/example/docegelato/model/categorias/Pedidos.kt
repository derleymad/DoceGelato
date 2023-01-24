package com.example.docegelato.model.categorias

data class Pedidos(
    val pedidos: MutableList<Pedido>,
    var address: Address?,
    var user: User?,
    var preco_total: Float
)


