package com.derleymad.docegelato.network

import com.derleymad.docegelato.model.categorias.Categorias
import com.derleymad.docegelato.model.categorias.Comida
import retrofit2.Call
import retrofit2.http.GET

interface ComidaApi {
    @GET("categorias.json")
    fun getCategorias(): Call<Categorias>

    @GET("destaques.json")
    fun getDestaques(): Call<ArrayList<Comida>>

    @GET("nova_categorias.json")
    fun getNovaCategorias(): Call<Categorias>
}

