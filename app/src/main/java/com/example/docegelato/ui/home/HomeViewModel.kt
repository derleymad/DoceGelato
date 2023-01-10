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

    private val _text = MutableLiveData<String>().apply { value = "This is home Fragment" }
    private val _comidas = MutableLiveData<List<Comida>>()

    private var _movieLiveData = MutableLiveData<Comida>()

    fun getComidas(){
        RetrofitInstance.api.getComidas().enqueue(object : Callback<Comida> {
            override fun onResponse(call: Call<Comida>, response: Response<Comida>) {
                if(response.body()!=null){
                    movieLiveData.value = response.body()
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<Comida>, t: Throwable) {
                Log.e("TAG",t.message.toString())
            }

        })
    }

    val movieLiveData = _movieLiveData
//
//    fun observeMovieLiveData() : LiveData<Comida>{
//        return movieLiveData
//    }

//    init {
//        getComidas()
//    }

}