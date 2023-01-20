package com.example.docegelato.model.categorias

data class Pedido (
    val comida_desc: String,
    val comida_id: Int,
    val comida_preco: Float?,
    val comida_title: String,
    val comida_desconto: Float,
    val image: String,
    val quantity: Int,
    val obs : String = "Nehuma observação"
)
