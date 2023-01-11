package com.example.docegelato.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.docegelato.R
import com.example.docegelato.model.categorias.Categorias
import com.example.docegelato.network.RetrofitInstance
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var _categoriaLiveData = MutableLiveData<Categorias>()
    private var _nomedaruaLiveData = MutableLiveData<String>()

    fun getCategorias(){
        RetrofitInstance.api.getCategorias().enqueue(object : Callback<Categorias>{
            override fun onResponse(call: Call<Categorias>, response: Response<Categorias>) {
                if(response.body()!=null){
                    categoriaLiveData.value = response.body()
                    Log.i("itsworking", response.body()!!.size.toString())
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<Categorias>, t: Throwable) {
                Log.e("TAG",t.message.toString())
            }
        })
    }

    fun loadHomeBanners(){

    }


//    fun getComidas(){
//        RetrofitInstance.api.getComidas().enqueue(object : Callback<Comida> {
//            override fun onResponse(call: Call<Comida>, response: Response<Comida>) {
//                if(response.body()!=null){
//                    comidaLiveData.value = response.body()
//                    Log.i("itsworking", response.body()!!.size.toString())
//                }else{
//                    return
//                }
//            }
//
//            override fun onFailure(call: Call<Comida>, t: Throwable) {
//                Log.e("TAG",t.message.toString())
//            }
//
//        })
//    }

    val nomedaruaLiveData = _nomedaruaLiveData
    val categoriaLiveData = _categoriaLiveData
//
//    fun observeMovieLiveData() : LiveData<Comida>{
//        return movieLiveData
//    }

//    init {
//        getComidas()
//    }

}