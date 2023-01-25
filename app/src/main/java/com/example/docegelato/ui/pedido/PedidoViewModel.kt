package com.example.docegelato.ui.pedido

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.docegelato.model.categorias.Pedidos
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PedidoViewModel : ViewModel() {
    var criarBadge= MutableLiveData(false)
    private var _dataRequest = MutableLiveData<ArrayList<Pedidos>>()
    private var database : FirebaseDatabase = Firebase.database
    private var myRef : DatabaseReference = database.getReference("pedidos")
    var loadingProgressBar = MutableLiveData(true)
    var dataRequest=  _dataRequest


    fun setListenerFirebaseEvent(){
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                loadingProgressBar.postValue(false)
                criarBadge.postValue(true)
                dataRequest.value = ArrayList()
                dataRequest.value?.clear()
                if (snapshot.exists()) {
                    for (pedido in snapshot.children) {
                        dataRequest.value?.add(pedido.getValue(Pedidos::class.java)!!)
                        Log.i("fala",dataRequest.value.toString())
                    }
                }else{}
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

}