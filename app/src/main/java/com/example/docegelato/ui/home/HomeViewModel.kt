package com.example.docegelato.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.docegelato.model.Comida
import com.example.docegelato.model.ComidaItem
import com.example.docegelato.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var _comidaLiveData = MutableLiveData<Comida>()
    private var _nomedaruaLiveData = MutableLiveData<String>()

    fun getComidas(){
        RetrofitInstance.api.getComidas().enqueue(object : Callback<Comida> {
            override fun onResponse(call: Call<Comida>, response: Response<Comida>) {
                if(response.body()!=null){
                    comidaLiveData.value = response.body()
                    Log.i("itsworking", response.body()!!.size.toString())
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<Comida>, t: Throwable) {
                Log.e("TAG",t.message.toString())
            }

        })
    }

    val nomedaruaLiveData = _nomedaruaLiveData
    val comidaLiveData = _comidaLiveData
//
//    fun observeMovieLiveData() : LiveData<Comida>{
//        return movieLiveData
//    }

//    init {
//        getComidas()
//    }

}