package com.derleymad.docegelato.ui.perfil.admin

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.derleymad.docegelato.databinding.FragmentAdminBinding
import com.derleymad.docegelato.model.pedidos.Pedidos
import com.derleymad.docegelato.ui.home.HomeViewModel
import com.derleymad.docegelato.ui.perfil.PerfilViewModel
import com.derleymad.docegelato.ui.perfil.adapters.AdminPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AdminFragment : Fragment() {

    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val perfilViewModel : PerfilViewModel by activityViewModels()
    private val list = ArrayList<Pedidos>()
    val db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()
        getAdminPedidos()
    }
    private fun getAdminPedidos() {
            db.collection("pedidos")
                .whereEqualTo("status", "Aguardando confirmação")
                .addSnapshotListener { snapshots, e->
                    if(e!=null){
                        Log.d("OTHERS",e.toString())
                        return@addSnapshotListener
                    }
                    if (snapshots != null) {
                        val list= ArrayList<Pedidos>()
                        for(i in snapshots.documents){
                            list.add(i.toObject(Pedidos::class.java)!!)
                        }
                        perfilViewModel.listAbertos.value = list
                    }
                }
        db.collection("pedidos")
            .whereEqualTo("status", "Pedido aceito")
            .addSnapshotListener { snapshots, e->
                if(e!=null){
                    Log.d("OTHERS",e.toString())
                    return@addSnapshotListener
                }
                if (snapshots != null) {
                    val listAceitos= ArrayList<Pedidos>()
                    for(i in snapshots.documents){
                        listAceitos.add(i.toObject(Pedidos::class.java)!!)
                    }
                    perfilViewModel.listAndamento.value = listAceitos
                }
            }

        db.collection("pedidos")
            .whereEqualTo("status","Pedido finalizado")
            .addSnapshotListener { snapshots, e->
                if(e!=null){
                    Log.d("OTHERS",e.toString())
                    return@addSnapshotListener
                }
                if (snapshots != null) {
                    val list= ArrayList<Pedidos>()
                    for(i in snapshots.documents){
                        list.add(i.toObject(Pedidos::class.java)!!)
                    }
                    perfilViewModel.listFinalizado.value = list
                }
            }
    }

    private fun setupTabLayout() {
        binding.apply {
            viewPager.adapter =
                AdminPagerAdapter(requireActivity())
            viewPager.offscreenPageLimit = 3
            TabLayoutMediator(tabLayoutAdmin, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Abertos"
                    1 -> "Em andamento"
                    2 -> "Fechados"
                    else -> throw  Resources.NotFoundException("Posição não encontrada!")
                }
            }.attach()
        }
    }

}