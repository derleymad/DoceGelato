package com.derleymad.docegelato.model.categorias

data class CategoriasItem(
    val comidas: List<Comida>,
    val description: String,
    val id: Int,
    val image: String,
    val title: String
)