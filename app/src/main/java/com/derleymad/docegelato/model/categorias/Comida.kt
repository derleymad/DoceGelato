package com.derleymad.docegelato.model.categorias

data class Comida(
    val comida_desc: String,
    val comida_id: Int,
    val comida_preco: Float?,
    val precos : ArrayList<HashMap<String,Any>?>?=null,
    val adicionais : ArrayList<Adicionais>?=null,
    val comida_title: String,
    val comida_desconto: Float,
    val image: String
)