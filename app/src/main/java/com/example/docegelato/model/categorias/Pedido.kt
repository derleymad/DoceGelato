package com.example.docegelato.model.categorias

data class Pedido(
    val comida_desc: String? = "",
    val comida_id: Int = 0,
    val comida_unique_id: Int = 0,
    val comida_preco: Float? = 0f,
    val comida_title: String = "",
    val comida_desconto: Float = 0f,
    val image: String = "",
    var quantity: Int = 0,
    val obs: String = "Nehuma observação"
)
