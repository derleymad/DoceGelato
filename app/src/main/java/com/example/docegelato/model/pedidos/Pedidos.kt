package com.example.docegelato.model.pedidos

import com.example.docegelato.model.categorias.Address
import com.example.docegelato.model.categorias.User

data class Pedidos(
    val pedidos: MutableList<Pedido>? = null,
    var address: Address? = null,
    var user: User? = null,
    var date : Long? = null,
    var preco_total: Float? = 0f,
    var uid : String? =null,
    var id : Long? = null,
    var status : String? = "Aguardando confirmação"

) {

}


