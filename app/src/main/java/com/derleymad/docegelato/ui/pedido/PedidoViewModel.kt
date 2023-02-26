package com.derleymad.docegelato.ui.pedido

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.derleymad.docegelato.model.pedidos.Pedidos
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PedidoViewModel : ViewModel() {
    var criarBadge = MutableLiveData(false)
    private var _dataRequest = MutableLiveData<ArrayList<Pedidos>>()
    private var firebaseAuth = Firebase.auth.currentUser
    private var database: FirebaseDatabase = Firebase.database
    private var myRef: DatabaseReference = database.getReference("users")
    var loadingProgressBar = MutableLiveData(true)
    var dataRequest = _dataRequest

}