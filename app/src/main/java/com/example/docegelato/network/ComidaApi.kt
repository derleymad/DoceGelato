package com.example.docegelato.network

import com.example.docegelato.model.categorias.Categorias
import com.example.docegelato.model.categorias.Comida
import retrofit2.Call
import retrofit2.http.GET

interface ComidaApi {
    @GET("categorias.json")
    fun getCategorias(): Call<Categorias>

    @GET("destaques.json")
    fun getDestaques(): Call<ArrayList<Comida>>
}

