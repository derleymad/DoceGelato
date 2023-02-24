package com.example.docegelato.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentMainBinding
import com.example.docegelato.extensions.navMainToCarrinho
import com.example.docegelato.extensions.navMainToMaps
import com.example.docegelato.model.categorias.Address
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.util.Utils.format
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    val homeViewModel : HomeViewModel by activityViewModels()
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
        Log.d("test","mainfragment created")
        binding.navView.setupWithNavController(navController)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObservers()
        setOnClickListeners()
        getLocateFromFireBase()
    }


    private fun getLocateFromFireBase() {
        db.collection("users")
            .document("clientes")
            .collection(auth.currentUser?.uid.toString())
            .document("location")
            .get()
            .addOnSuccessListener{
                if(it.exists()){
                    homeViewModel.address.value = it.toObject(Address::class.java)
                }else{
                   findNavController().navMainToMaps()
                }
            }
            .addOnFailureListener {
            }
    }

    private fun setOnClickListeners() {
        binding.included.lnCarrinhoFlutuante.setOnClickListener {
            it.apply { findNavController().navMainToCarrinho() }
        }
    }

    private fun startObservers() {
        homeViewModel.isPedidoFeitoLiveData.observe(viewLifecycleOwner){
            if(it){
                binding.included.lnCarrinhoFlutuante.visibility = View.VISIBLE
            }else{
                binding.included.lnCarrinhoFlutuante.visibility = View.GONE
            }
        }
        homeViewModel.precoTotalLiveData.observe(viewLifecycleOwner) {
            binding.included.totalPriceCarrinhoFlutuante.text = format(it)
        }
    }

}