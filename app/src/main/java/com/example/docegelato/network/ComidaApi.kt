package com.example.docegelato.network

import com.example.docegelato.model.Comida
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ComidaApi {
    @GET("comidas.json")
    fun getComidas() : Call<Comida>
}

