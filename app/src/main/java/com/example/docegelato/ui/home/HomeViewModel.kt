package com.example.docegelato.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.docegelato.R
import com.example.docegelato.model.categorias.*
import com.example.docegelato.network.RetrofitInstance
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
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
    private var _precoTotalLiveData = MutableLiveData<Float>(0f)
    var user= MutableLiveData<User>()
    private var _addressLiveData = MutableLiveData<Address>()
    var isLoadingContent = MutableLiveData(true)

    var auth : FirebaseAuth
    val listPedidoFeitoLiveData = _listPedidoFeitoLiveData
    val precoTotalLiveData = _precoTotalLiveData

    init {
        listPedidoFeitoLiveData.value = Pedidos(mutableListOf(),null,null,0f)
        auth = FirebaseAuth.getInstance()
        user.value=User(nome = auth.currentUser?.displayName.toString(),
            imagemPerfil = auth.currentUser?.photoUrl.toString(),
            uid = auth.currentUser?.uid.toString(),
            numero_celular = auth.currentUser?.phoneNumber.toString()
        )
    }

    fun getCategorias(){
        RetrofitInstance.api.getCategorias().enqueue(object : Callback<Categorias>{
            override fun onResponse(call: Call<Categorias>, response: Response<Categorias>) {
                if(response.body()!=null){
                    isLoadingContent.value = false
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

    fun removeComidaDosPedidos(pedido: Pedido){
        // ATUALIZA O PRECO TOTAL
        precoTotalLiveData.value = precoTotalLiveData.value?.minus(pedido.comida_preco?.times(pedido.quantity) ?: 0f)
        // REMOVE O PRODUTO DO LIVEDATA
        listPedidoFeitoLiveData.value?.pedidos?.remove(pedido)
        if(listPedidoFeitoLiveData.value?.pedidos?.isEmpty() == true){
            isPedidoFeitoLiveData.value = false
        }
    }

    fun diminuirQuantidade(){
        quantityLiveData.value = quantityLiveData.value?.minus(1)
    }
    fun aumentarQuantidade(){
        quantityLiveData.value = quantityLiveData.value?.plus(1)
    }

    fun setComidaToPedidos(comida: Comida, user: User, address: Address){
       listPedidoFeitoLiveData.value?.preco_total = quantityLiveData.value?.times(comida.comida_preco!!)!!
       listPedidoFeitoLiveData.value?.address  = address
       listPedidoFeitoLiveData.value?.user = user
       precoTotalLiveData.value = precoTotalLiveData.value!! + listPedidoFeitoLiveData.value?.preco_total!!
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
                    isLoadingContent.value = false
                }else{
                    return
                }
            }
            override fun onFailure(call: Call<ArrayList<Comida>>, t: Throwable) {
            }
        })
    }


    fun clearPedidosAndPrices() {
        listPedidoFeitoLiveData.value?.pedidos?.clear()
        listPedidoFeitoLiveData.value?.preco_total = 0f
    }

   val nomedaruaLiveData = _nomedaruaLiveData
   val categoriaLiveData = _categoriaLiveData
   val destaquesLiveData = _destaquesLiveData
   val idLiveData = _idLiveData
   val quantityLiveData = _quantityLiveData
   val isPedidoFeitoLiveData = _isPedidoFeitoLiveData
   val obsLiveData = _obsLiveData
   val address= _addressLiveData

}