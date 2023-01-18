package com.example.docegelato.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.docegelato.model.categorias.Categorias
import com.example.docegelato.model.categorias.Comida
import com.example.docegelato.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var _categoriaLiveData = MutableLiveData<Categorias>()
    private var _nomedaruaLiveData = MutableLiveData<String>()
    private var _destaquesLiveData = MutableLiveData<ArrayList<Comida>>()
    private var _idLiveData = MutableLiveData<Int>()
    private var _quantityLiveData = MutableLiveData<Int>(0)
    private var _priceLiveData = MutableLiveData<Float>()
    private var _actualComidaLiveData = MutableLiveData<Comida>()


    fun getCategorias(){
        RetrofitInstance.api.getCategorias().enqueue(object : Callback<Categorias>{
            override fun onResponse(call: Call<Categorias>, response: Response<Categorias>) {
                if(response.body()!=null){
                    categoriaLiveData.value = response.body()
                    Log.i("categoriesworking", response.body()!!.toString())
                }else{
                    return
                }
            }
            override fun onFailure(call: Call<Categorias>, t: Throwable) {
                Log.e("TAG",t.message.toString())
            }
        })
    }

    fun getDestaques() {
        RetrofitInstance.api.getDestaques().enqueue(object : Callback<ArrayList<Comida>> {
            override fun onResponse(
                call: Call<ArrayList<Comida>>,
                response: Response<ArrayList<Comida>>
            ) {
                if(response.body()!=null){
                    destaquesLiveData.value = response.body()
                    Log.i("itsworking", response.body()!!.toString())
                }else{
                    return
                }
            }
            override fun onFailure(call: Call<ArrayList<Comida>>, t: Throwable) {
            }
        })
    }
        val nomedaruaLiveData = _nomedaruaLiveData
        val categoriaLiveData = _categoriaLiveData
        val destaquesLiveData = _destaquesLiveData
        val idLiveData = _idLiveData
        val quantityLiveData = _quantityLiveData
        val priceLiveData = _priceLiveData
        val actualComidaLiveData = _actualComidaLiveData

}