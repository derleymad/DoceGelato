package com.example.docegelato.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.docegelato.model.categorias.Categorias
import com.example.docegelato.model.categorias.Comida
import com.example.docegelato.model.categorias.Pedido
import com.example.docegelato.model.categorias.Pedidos
import com.example.docegelato.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var _categoriaLiveData = MutableLiveData<Categorias>()
    private var _nomedaruaLiveData = MutableLiveData<String>()
    private var _destaquesLiveData = MutableLiveData<ArrayList<Comida>>()
    private var _idLiveData = MutableLiveData<Int>()
    private var _quantityLiveData = MutableLiveData(1)
    private var _isPedidoFeitoLiveData = MutableLiveData<Boolean>()
    private var _listPedidoFeitoLiveData = MutableLiveData<Pedidos>()
    private var _obsLiveData = MutableLiveData<String>()
    val listPedidoFeitoLiveData = _listPedidoFeitoLiveData

    init {
        listPedidoFeitoLiveData.value = Pedidos(mutableListOf(),0f)
    }

    fun getCategorias(){
        RetrofitInstance.api.getCategorias().enqueue(object : Callback<Categorias>{
            override fun onResponse(call: Call<Categorias>, response: Response<Categorias>) {
                if(response.body()!=null){
                    categoriaLiveData.value = response.body()
                }else{
                    return
                }
            }
            override fun onFailure(call: Call<Categorias>, t: Throwable) {
                Log.e("TAG",t.message.toString())
            }
        })
    }
    fun setComidaToPedidos(comida: Comida){
        listPedidoFeitoLiveData.value?.preco_total = listPedidoFeitoLiveData.value?.preco_total?.plus(comida.comida_preco!!*quantityLiveData.value!!)!!
        listPedidoFeitoLiveData.value?.pedidos?.add(
            Pedido(
                comida_desc = comida.comida_desc,
                comida_id = comida.comida_id,
                comida_preco = comida.comida_preco,
                comida_title = comida.comida_title,
                comida_desconto = comida.comida_desconto,
                image = comida.image,
                quantity = quantityLiveData.value ?: 0,
                obs = obsLiveData.value.toString()
            )
        )
    }

    fun getDestaques() {
        RetrofitInstance.api.getDestaques().enqueue(object : Callback<ArrayList<Comida>> {
            override fun onResponse(
                call: Call<ArrayList<Comida>>,
                response: Response<ArrayList<Comida>>
            ) {
                if(response.body()!=null){
                    destaquesLiveData.value = response.body()
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
        val isPedidoFeitoLiveData = _isPedidoFeitoLiveData
        val obsLiveData = _obsLiveData
}