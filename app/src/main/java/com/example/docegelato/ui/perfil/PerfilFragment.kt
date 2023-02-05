package com.example.docegelato.ui.perfil

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentPerfilBinding
import com.example.docegelato.model.pedidos.Pedidos
import com.example.docegelato.ui.home.HomeViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class PerfilFragment : Fragment() {
    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val adapter = AdapterPedidosAdmin()
    private val list = ArrayList<Pedidos>()
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        startObservers()
        startEventClickListeners()

        Picasso.get().load(homeViewModel.auth.currentUser?.photoUrl)
            .placeholder(R.drawable.placeholder).into(binding.imgPerfilPhoto)
        binding.perfilName.text = homeViewModel.auth.currentUser?.displayName
        return binding.root
    }

    private fun startEventClickListeners() {
        binding.closePerfilBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.sair.setOnClickListener {
            homeViewModel.auth.signOut()
            findNavController().popBackStack()
            requireActivity().finish()
        }

    }

    fun startObservers(){
        homeViewModel.address.observe(viewLifecycleOwner){
            binding.localizacao.text = "${it.bairro+", " +it.rua+", "+it.numero_da_casa}"
        }
        homeViewModel.isAdmin.observe(viewLifecycleOwner){
            when (it){
                true -> {
                    startRecyclerView()
                    binding.admin.visibility = if(it)View.VISIBLE else View.GONE
                    adminPedidos()
                }
                else ->{
                }
            }
        }
    }

    fun startRecyclerView(){
        binding.rvAdmin.adapter = adapter
        binding.rvAdmin.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun adminPedidos(){
        val myref = db.collection("pedidos")
        myref.addSnapshotListener { value, error ->
            list.clear()
            if(error!=null){
                Log.i("TAG","Error with null snapshot")
            }
            if (value != null) {
                Log.d("othersTeste", "New city: " + value.documents.toString())
                for(i in value.documents){
                    if(i.exists()){
                        list.add(i.toObject(Pedidos::class.java)!!)
                    }
                }
                adapter.setListToRecyclerView(list)
            }
        }
    }
}