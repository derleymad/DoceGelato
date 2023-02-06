package com.example.docegelato.ui.perfil.admin

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docegelato.databinding.FragmentAdminBinding
import com.example.docegelato.model.pedidos.Pedidos
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.ui.home.adapters.PagerAdapter
import com.example.docegelato.ui.perfil.adapters.AdapterPedidosAdmin
import com.example.docegelato.ui.perfil.adapters.AdminPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminFragment : Fragment() {

    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val adapter = AdapterPedidosAdmin()
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
//        adminPedidos()
        setupTabLayout()
    }

    private fun adminPedidos() {
        val pedidos = db.collection("pedidos")
        val andamento = db.collection("andamento")
        val abertos = db.collection("abertos")

        andamento.addSnapshotListener { value, error ->
            if (error != null) {
            }
            if (value != null) {
                for (i in value.documents) {
                    if (i.exists()) {
                    }
                }

            }
        }
        pedidos.addSnapshotListener { value, error ->
            list.clear()
            if (error != null) {
                Log.i("TAG", "Error with null snapshot")
            }
            if (value != null) {
                Log.d("othersTeste", "New city: " + value.documents.toString())
                for (i in value.documents) {
                    if (i.exists()) {
                        list.add(i.toObject(Pedidos::class.java)!!)
                    }
                }
//                adapter.setListToRecyclerView(list)
            }
        }
    }
    private fun setupTabLayout() {
        binding.apply {
            viewPager.adapter =
                AdminPagerAdapter(requireActivity())
            viewPager.offscreenPageLimit = 3
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
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