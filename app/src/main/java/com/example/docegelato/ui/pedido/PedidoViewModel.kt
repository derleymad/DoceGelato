package com.example.docegelato.ui.pedido

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.docegelato.model.categorias.Pedidos
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PedidoViewModel : ViewModel() {
    var criarBadge= MutableLiveData(false)
    private var _dataRequest = MutableLiveData<ArrayList<Pedidos>>()
    private var firebaseAuth = Firebase.auth.currentUser
    private var database : FirebaseDatabase = Firebase.database
    private var myRef : DatabaseReference = database.getReference("users")
    var loadingProgressBar = MutableLiveData(true)
    var dataRequest=  _dataRequest

    fun setListenerFirebaseEvent(){
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                loadingProgressBar.postValue(false)
                criarBadge.postValue(true)
                dataRequest.value = ArrayList()
                dataRequest.value?.clear()

                snapshot.children.forEach {user->
                    if(user.key==firebaseAuth?.uid.toString()){
                        user.children.forEach {pedido->
                            dataRequest.value?.add(pedido.getValue(Pedidos::class.java)!!)
                        }
                    }

                }

//                if (snapshot.exists()) {
//                    for (user in snapshot.children) {
//                        if (user.value.toString()==firebaseAuth?.uid.toString()){
//                        dataRequest.value?.add(user.getValue(Pedidos::class.java)!!)
//                            Log.i("fala", user.value.toString())
//                        }
//                        Log.i("fala", firebaseAuth?.uid.toString())
//                        Log.i("fala",snapshot.value.toString())
//                    }
//                }else{
//                    Log.i("fala","entrou no else")
//                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

}