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
import com.example.docegelato.model.pedidos.Pedido
import com.example.docegelato.model.pedidos.Pedidos
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.ui.home.adapters.PagerAdapter
import com.example.docegelato.ui.perfil.PerfilViewModel
import com.example.docegelato.ui.perfil.adapters.AdapterPedidosAdmin
import com.example.docegelato.ui.perfil.adapters.AdminPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class AdminFragment : Fragment() {

    private var _binding: FragmentAdminBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val perfilViewModel : PerfilViewModel by activityViewModels()
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
        getAdminPedidos()
        setupTabLayout()
    }

    private fun getAdminPedidos() {
        val andamento = db.collection("andamento")
        val abertos = db.collection("abertos")
        val fechados = db.collection("fechados")


//        val ref = db.collection("abertos")
//        ref.whereEqualTo("status","Pedido aceito")
//            .addSnapshotListener{value,error->
//                Log.i("testandolog",value?.documents.toString())
//                val list = ArrayList<Pedidos>()
//                for(i in value!!.documents){
//                    if(i.exists()){
//                        list.add(i.toObject(Pedidos::class.java)!!)
//                    }
//                }
////                pedidoViewModel.dataRequest.value = list
////                pedidoViewModel.loadingProgressBar.value = false
//            }

        abertos.addSnapshotListener { value, error ->
           value?.query?.whereEqualTo("status","Pedido feito")?.get()?.addOnSuccessListener {

           for(i in value?.documentChanges!!){
              Log.i("testandolog2",i.document.toString())
           }
           val list = ArrayList<Pedidos>()
               Log.i("testandoquery",it.documents.toString())
               if(it.documents.isNotEmpty()){
                   for(i in it.documents){
                       if(i.exists()){
                           list.add(i.toObject(Pedidos::class.java)!!)
                       }
                   }
               }
               perfilViewModel.listAbertos.value = list
           }
            value?.query?.whereEqualTo("status","Pedido aceito")?.get()?.addOnSuccessListener {
                val list = ArrayList<Pedidos>()
                Log.i("testandoquery",it.documents.toString())
                if(it.documents.isNotEmpty()){
                    for(i in it.documents){
                        if(i.exists()){
                            list.add(i.toObject(Pedidos::class.java)!!)
                        }
                    }
                }
                perfilViewModel.listAndamento.value = list
            }
            value?.query?.whereEqualTo("status","Pedido entregue")?.get()?.addOnSuccessListener {
                val list = ArrayList<Pedidos>()
                Log.i("testandoquery",it.documents.toString())
                if(it.documents.isNotEmpty()){
                    for(i in it.documents){
                        if(i.exists()){
                            list.add(i.toObject(Pedidos::class.java)!!)
                        }
                    }
                }
                perfilViewModel.listFechados.value = list
            }
//            val list = ArrayList<Pedidos>()
//            if (error != null) {
//                Log.i("errorNull",error.toString())
//            }
//            if (value != null) {
//                for (i in value.documents) {
//                    if (i.exists()) {
//                        Log.i("adicionou","adicionaou")
//                        list.add(i.toObject(Pedidos::class.java)!!)
//                    }
//                }
//                perfilViewModel.listAbertos.value = list
//            }else{
//                Log.i("firestoreteste","ta entrando no else")
//            }
        }

//
//            abertos.addSnapshotListener { value, error ->
//                val list = ArrayList<Pedidos>()
//                if (error != null) {
//                    Log.i("errorNull",error.toString())
//                }
//                if (value != null) {
//                    for (i in value.documents) {
//                        if (i.exists()) {
//                            Log.i("adicionou","adicionaou")
//                            list.add(i.toObject(Pedidos::class.java)!!)
//                        }
//                    }
//                    perfilViewModel.listAbertos.value = list
//                }else{
//                    Log.i("firestoreteste","ta entrando no else")
//                }
//            }

//
//        fechados.addSnapshotListener { value, error ->
//            val list = ArrayList<Pedidos>()
//            if (error != null) {
//            } else {
//                if (value != null) {
//                    for (i in value.documents) {
//                        if (i.exists()) {
//                            list.add(i.toObject(Pedidos::class.java)!!)
//                        }
//                    }
//                    perfilViewModel.listFechados.value = list
//                }
//            }
//        }
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