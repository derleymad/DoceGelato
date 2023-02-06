package com.example.docegelato.ui.perfil.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docegelato.databinding.FragmentBaseAdminBinding
import com.example.docegelato.ui.home.HomeViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BaseAdminFragment(value : Int) : Fragment() {

    private var _binding: FragmentBaseAdminBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBaseAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        startRecyclerView()
    }
    fun startRecyclerView(){
        binding.rvAdmin.layoutManager = LinearLayoutManager(requireContext())
    }

}