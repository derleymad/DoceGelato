package com.example.docegelato.ui.perfil

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentPerfilBinding
import com.example.docegelato.model.categorias.Address
import com.example.docegelato.model.categorias.Pedidos
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.util.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class PerfilFragment : Fragment() {
    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)

        startObservers()

//
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (i in snapshot.children) {
//                    Log.i("foi?", i.key.toString())
//                    Log.i("foi?", homeViewModel.auth.currentUser?.uid.toString())
//                    if (i.key?.equals(homeViewModel.auth.currentUser?.uid.toString()) == true) {
//                        binding.admin.visibility = View.VISIBLE
//                        return
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//            }
//        })

        binding.closePerfilBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.sair.setOnClickListener {
            homeViewModel.auth.signOut()
            findNavController().popBackStack()
            requireActivity().finish()
        }

        Picasso.get().load(homeViewModel.auth.currentUser?.photoUrl)
            .placeholder(R.drawable.placeholder).into(binding.imgPerfilPhoto)
        binding.perfilName.text = homeViewModel.auth.currentUser?.displayName
        binding.localizacao.text = homeViewModel.address.value.toString()

        return binding.root
    }

    fun startObservers(){
        homeViewModel.address.observe(viewLifecycleOwner){
            binding.localizacao.text = it.toString()
        }
    }
}