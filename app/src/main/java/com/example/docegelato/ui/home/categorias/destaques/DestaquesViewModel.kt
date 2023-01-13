package com.example.docegelato.ui.home.categorias.destaques

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.docegelato.model.categorias.Comida
import com.example.docegelato.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestaquesViewModel : ViewModel() {
    private var _destaquesLiveData = MutableLiveData<ArrayList<Comida>>()
    var destaquesLiveData = _destaquesLiveData

    fun getDestaques(){
        RetrofitInstance.api.getDestaques().enqueue(object : Callback<ArrayList<Comida>>{
            override fun onResponse(
                call: Call<ArrayList<Comida>>,
                response: Response<ArrayList<Comida>>
            ) {
                if(response.body() != null){
                    destaquesLiveData.value = response.body()
                    Log.i("reponsedestaque",response.body().toString())

                }else{return}
            }

            override fun onFailure(call: Call<ArrayList<Comida>>, t: Throwable) {
                Log.e("TAG",t.message.toString())
            }
        })
    }


}