package com.derleymad.docegelato.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: ComidaApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://derleymad.github.io/projeto-delivery/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ComidaApi::class.java)
    }
}