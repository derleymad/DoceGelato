package com.example.docegelato.ui.perfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentPerfilBinding
import com.example.docegelato.extensions.navPerfilToAdmin
import com.example.docegelato.model.pedidos.Pedidos
import com.example.docegelato.ui.home.HomeViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class PerfilFragment : Fragment() {
    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val list = ArrayList<Pedidos>()
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObservers()
        startEventClickListeners()
        loadPerfilImage()
    }

    private fun loadPerfilImage() {
        Picasso.get().load(homeViewModel.auth.currentUser?.photoUrl)
            .placeholder(R.drawable.placeholder).into(binding.imgPerfilPhoto)
        binding.perfilName.text = homeViewModel.auth.currentUser?.displayName
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
        binding.admin.setOnClickListener {
            navPerfilToAdmin()
        }

    }

    fun startObservers(){
        homeViewModel.address.observe(viewLifecycleOwner){
            binding.localizacao.text = "${it.bairro+", " +it.rua+", "+it.numero_da_casa}"
        }
        homeViewModel.isAdmin.observe(viewLifecycleOwner){
            when (it){
                true -> {

                    binding.admin.visibility = if(it)View.VISIBLE else View.GONE
                }
                else ->{
                }
            }
        }
    }

}