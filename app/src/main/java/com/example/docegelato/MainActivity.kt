package com.example.docegelato

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.docegelato.databinding.ActivityMainBinding
import com.example.docegelato.model.categorias.Address
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.ui.pedido.PedidoViewModel
import com.example.docegelato.util.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView
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
//        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
//        val navView: BottomNavigationView = binding.navView
//        var navController = findNavController(R.id.nav_host_fragment_activity_main)
//        navView.setupWithNavController(navController)
//        startObservers()
//        pedidoViewModel.setListenerFirebaseEvent()
//
//        db.collection("users")
//            .document("clientes")
//            .collection(auth.currentUser?.uid.toString())
//            .document("location")
//            .get()
//            .addOnSuccessListener{
////                Log.i("dataPerfil", it.toObject(Address::class.java).toString())
//                homeViewModel.address.value = it.toObject(Address::class.java)
//            }
//            .addOnFailureListener {
//            }
//
////        val user = hashMapOf(
////            "teste" to "doidin"
////        )
////        val teste = Pedidos()
////        db.collection("users")
////            .document("qualquer")
////            .set(teste)
////            .addOnSuccessListener{
////                Log.i("teste","Sucesso")
////            }
////            .addOnFailureListener {
////                Log.i("teste","Falha")
////            }
////
////        db.collection("users")
////            .document("qualquer")
////            .get()
////            .addOnSuccessListener {
////                Log.d("outro",it.data.toString())
////            }
////            .addOnFailureListener{
////                Log.d("outro","zebra")
////            }
//
//        homeViewModel.getDestaques()
//        homeViewModel.getCategorias()
//
//        if(homeViewModel.address.value?.rua.isNullOrBlank()){
////            navController.navigate(R.id.newUserLoginFragment)
//            homeViewModel.address.value.toString()
//        }
//
//        binding.lnCarrinhoFlutuante.setOnClickListener {
//            navController.navHomeToCarrinho()
//            binding.lnCarrinhoFlutuante.visibility = View.GONE
//        }
//    }
//
//    private fun createOrRemoveBadge(criar: Boolean) {
//        val navBar: BottomNavigationView = findViewById(R.id.nav_view)
//        when (criar) {
//            true -> navBar.getOrCreateBadge(R.id.navigation_pedido).number++
//            else -> navBar.removeBadge(R.id.navigation_pedido)
//        }
//    }
//
//    private fun startObservers() {
//        homeViewModel.hideNavBar.observe(this) {
//            toggle(it)
//        }
//        homeViewModel.precoTotalLiveData.observe(this) {
//            binding.totalPriceCarrinhoFlutuante.text = Utils.format(it)
//        }
//        pedidoViewModel.criarBadge.observe(this) {
//            createOrRemoveBadge(it)
//        }
//        homeViewModel.hideCarrinhoFlutuante.observe(this) {
//            binding.lnCarrinhoFlutuante.visibility = if (it) View.GONE else View.VISIBLE
//        }
//    }
//
//    private fun toggle(it: Boolean) {
//        val transition: Transition = Slide(Gravity.BOTTOM)
//        transition.duration = 300
//        transition.addTarget(binding.navView)
//        TransitionManager.beginDelayedTransition(binding.root, transition)
//        binding.navView.visibility = if (it) View.GONE else View.VISIBLE
    }
}