package com.example.docegelato.ui.perfil.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentBaseAdminBinding
import com.example.docegelato.model.pedidos.Pedidos
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.ui.perfil.PerfilViewModel
import com.example.docegelato.ui.perfil.adapters.AdapterEmAndamento
import com.example.docegelato.ui.perfil.adapters.AdapterPedidosAdmin
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BaseAdminFragment(val valueBase : Int) : Fragment() {

    private var _binding: FragmentBaseAdminBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val perfilViewModel : PerfilViewModel by activityViewModels()
    private lateinit var adapter: AdapterPedidosAdmin
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
        startObservers()

    }
    private fun startObservers() {
        when(this.valueBase){
            0 -> {
                perfilViewModel.listAbertos.observe(viewLifecycleOwner){
                    if(it!=null){
                        startRecyclerView()
                        adapter.setListToRecyclerView(it)
                        if(it.isNotEmpty()){
                            binding.included.root.visibility = View.GONE
                        }else{
                            binding.included.root.visibility = View.VISIBLE
                        }
//                        requireActivity().findViewById<TabLayout>(R.id.tab_layout_admin).getTabAt(this.valueBase)?.orCreateBadge!!.number = it.size
                    }
                }
            }
            1 -> {
                perfilViewModel.listAndamento.observe(viewLifecycleOwner){
                    if(it!=null){
                        startRecyclerView()
                        adapter.setListToRecyclerView(it)
                        if(it.isNotEmpty()){
                            binding.included.root.visibility = View.GONE
                        }else{
                            binding.included.root.visibility = View.VISIBLE
                        }
                    }
                }
            }
            2 -> {
                perfilViewModel.listFinalizado.observe(viewLifecycleOwner){
                    startRecyclerView()
                    adapter.setListToRecyclerView(it)
                    if(it.isNotEmpty()){
                        binding.included.root.visibility = View.GONE
                    }else{
                        binding.included.root.visibility = View.VISIBLE
                    }
                }
            }
            else ->{}
        }
    }

    fun startRecyclerView(){
        adapter = AdapterPedidosAdmin(
            pedidoFeitoOnClickListener = {
                db.collection("pedidos")
                    .document(it.toString())
                    .update("status","Pedido aceito")
            },
            pedidoRemovidoOnClickListener = {
                db.collection("pedidos")
                    .document(it.toString())
                    .update("status","Pedido finalizado")
            }
        ,this.valueBase
        )
        binding.rvAdmin.adapter = adapter
        val layout = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,true)
        layout.stackFromEnd= true
        binding.rvAdmin.layoutManager = layout
    }

}