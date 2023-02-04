package com.example.docegelato

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.docegelato.databinding.ActivityMainBinding
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.ui.pedido.PedidoViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var homeViewModel: HomeViewModel
    val pedidoViewModel: PedidoViewModel by viewModels()
    val auth = FirebaseAuth.getInstance()
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        homeViewModel.getDestaques()
        homeViewModel.getCategorias()
        getAdminUserFromFirebase()

    }

    private fun getAdminUserFromFirebase(){
        db.collection("users")
            .document("clientes")
            .collection(auth.currentUser?.uid.toString())
            .document("admin")
            .get()
            .addOnSuccessListener{
                if(it.exists()){
                    homeViewModel.isAdmin.value = it.data?.values?.first()?.equals(true)
                }else{
                    homeViewModel.isAdmin.value = false
                    Log.i("isadmin","nao é admin")
                }
            }
            .addOnFailureListener {
            }
    }
}