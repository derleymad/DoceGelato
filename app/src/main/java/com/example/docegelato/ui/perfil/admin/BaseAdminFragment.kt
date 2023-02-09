package com.example.docegelato.ui.perfil.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docegelato.databinding.FragmentBaseAdminBinding
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.ui.perfil.PerfilViewModel
import com.example.docegelato.ui.perfil.adapters.AdapterPedidosAdmin
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BaseAdminFragment(val valueBase : Int) : Fragment() {

    private var _binding: FragmentBaseAdminBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val perfilViewModel : PerfilViewModel by activityViewModels()
    private lateinit var adapter : AdapterPedidosAdmin
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
        startRecyclerView()
        startObservers()
    }

    private fun startObservers() {
        when(this.valueBase){
            0 -> {
                perfilViewModel.listAbertos.observe(viewLifecycleOwner){
                    if(it!=null){
                        adapter.setListToRecyclerView(it)
                        Log.i("chamoui","chamo no update")
                    }
                }
            }
            1 -> {
                perfilViewModel.listAndamento.observe(viewLifecycleOwner){
                    if(it!=null){
                        adapter.setListToRecyclerView(it)
                    }
                }
            }
            2 -> {
                perfilViewModel.listFechados.observe(viewLifecycleOwner){
                    if(it!=null){
                        adapter.setListToRecyclerView(it)
                    }
                }
            }
            else ->{}
        }
    }

    fun startRecyclerView(){
        adapter = AdapterPedidosAdmin{
            db.collection("abertos")
                .document(it.toString())
                .update("status","Pedido aceito")
        }
        binding.rvAdmin.adapter = adapter
        binding.rvAdmin.layoutManager = LinearLayoutManager(requireContext())
    }

}