package com.example.docegelato.model.categorias

data class Address(
    var bairro: String? = "",
    var rua: String? = "",
    val cidade: String? = "",
    var numero_da_casa: String? = "",
    var ponto_referencia: String? = ""
) {}
