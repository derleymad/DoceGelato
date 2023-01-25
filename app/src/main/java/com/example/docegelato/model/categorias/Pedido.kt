package com.example.docegelato.model.categorias

data class Pedido (
    val comida_desc: String? = "",
    val comida_id: Int = 0,
    val comida_preco: Float? = 0f,
    val comida_title: String = "",
    val comida_desconto: Float = 0f,
    val image: String = "",
    val quantity: Int = 0,
    val obs : String = "Nehuma observação"
)
