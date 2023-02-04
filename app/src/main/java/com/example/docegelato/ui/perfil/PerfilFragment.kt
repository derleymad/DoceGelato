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
import com.example.docegelato.ui.home.HomeViewModel
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
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
        adminPedidos()
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
        return binding.root
//        binding.localizacao.text = "${homeViewModel.address.value?.bairro+", " +homeViewModel.address.value?.rua+", "+homeViewModel.address.value?.numero_da_casa}"
    }

    fun startObservers(){
        homeViewModel.address.observe(viewLifecycleOwner){
            binding.localizacao.text = "${it.bairro+", " +it.rua+", "+it.numero_da_casa}"
        }
        homeViewModel.isAdmin.observe(viewLifecycleOwner){
            Log.i("isadminObserve",it.toString())
            binding.admin.visibility = if(it)View.VISIBLE else View.GONE
        }
    }

    private fun adminPedidos(){

        val myref = db.collection("pedidos")
        myref.addSnapshotListener { value, error ->
            if (value != null) {
                Log.d("othersTeste", "New city: " + value.documents.toString())
            }
        }

//        val docRef = db.collection("pedidos").document("recentes")
//        docRef.addSnapshotListener{value,error ->
//            if(error!=null){
//                Log.i("listenSnapshot","fail to listen")
//            }
//
//            Log.i("listenSnapshot",value.toString())
//            if(value!=null && value.exists()){
//                Log.i("listenSnapshot","changes")
//                Log.i("listenSnapshot",value.toString())
//            }
//        }
    }
}