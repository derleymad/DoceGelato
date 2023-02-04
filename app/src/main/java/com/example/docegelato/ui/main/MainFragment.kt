package com.example.docegelato.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentMainBinding
import com.example.docegelato.extensions.navMainToCarrinho
import com.example.docegelato.model.categorias.Address
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.ui.pedido.PedidoViewModel
import com.example.docegelato.util.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    val homeViewModel : HomeViewModel by activityViewModels()
    val pedidoViewModel: PedidoViewModel by activityViewModels()
    val auth = FirebaseAuth.getInstance()
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        val navHost = childFragmentManager.findFragmentById(R.id.nav_host_fragment_fragment_main) as NavHostFragment
        val navController = navHost.navController
        binding.navView.setupWithNavController(navController)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObservers()
//        getAdressFromFirebase()

        db.collection("users")
            .document("clientes")
            .collection(auth.currentUser?.uid.toString())
            .document("location")
            .get()
            .addOnSuccessListener{
                if(it.exists()){
                    homeViewModel.address.value = it.toObject(Address::class.java)
                }else{
                    findNavController().navigate(R.id.action_mainFragment_to_mapsFragment2)
                }
            }
            .addOnFailureListener {
            }

        binding.lnCarrinhoFlutuante.setOnClickListener {
            it.apply { findNavController().navMainToCarrinho() }
        }

    }

    //TODO MELHORAR CREATE E REMOVE BADGES
//    private fun createOrRemoveBadge(criar: Boolean) {
//        when (criar) {
//            true -> binding.navView.getOrCreateBadge(R.id.navigation_pedido).number++
//            else -> binding.navView.removeBadge(R.id.navigation_pedido)
//        }
//    }

    private fun startObservers() {
        homeViewModel.precoTotalLiveData.observe(viewLifecycleOwner) {
            binding.totalPriceCarrinhoFlutuante.text = Utils.format(it)
        }
        homeViewModel.isPedidoFeitoLiveData.observe(viewLifecycleOwner){
            if(it){
                binding.lnCarrinhoFlutuante.visibility = View.VISIBLE
            }else{
                binding.lnCarrinhoFlutuante.visibility = View.GONE
            }
        }
    }

}