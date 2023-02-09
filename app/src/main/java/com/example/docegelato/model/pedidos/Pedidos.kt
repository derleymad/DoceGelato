package com.example.docegelato.model.pedidos

import com.example.docegelato.model.categorias.Address
import com.example.docegelato.model.categorias.User

data class Pedidos(
    val pedidos: MutableList<Pedido>? = null,
    var address: Address? = null,
    var user: User? = null,
    var date : String? = "0",
    var preco_total: Float? = 0f,
    var uid : String? =null,
    var id : Long? = null,
    var status : String? = "Pedido feito"

) {

}


