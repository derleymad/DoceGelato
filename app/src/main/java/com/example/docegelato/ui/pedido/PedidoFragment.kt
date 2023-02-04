package com.example.docegelato.ui.pedido

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentPedidoBinding
import com.example.docegelato.model.categorias.Address
import com.example.docegelato.model.categorias.Pedido
import com.example.docegelato.model.categorias.Pedidos
import com.example.docegelato.util.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PedidoFragment : Fragment() {

    private var _binding: FragmentPedidoBinding? = null
    private val binding get() = _binding!!
    private val pedidoViewModel: PedidoViewModel by activityViewModels()
    private lateinit var adapter: PedidoFeitoAdapter
    private var db = Firebase.firestore
    private var auth = FirebaseAuth.getInstance()
    private var data = ArrayList<Pedidos>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPedidoBinding.inflate(inflater, container, false)
        prepareRecyclerView()
        binding.rvPedido.addOnItemTouchListener(object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_MOVE ->
                        pedidoViewModel.criarBadge.value = false
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startObservers()
//        db.collection("pedidos")
//            .document("recentes")
//            .collection(auth.currentUser?.uid.toString())
//            .get()
//            .addOnSuccessListener{
//                val list = ArrayList<Pedidos>()
//                if(it.documents.isNotEmpty()){
//                    for(i in it.documents){
//                        if (i.exists()){
//                            list.add(i.toObject(Pedidos::class.java)!!)
//                        }else{
//                            list.clear()
//                        }
//                    }
//                    adapter.setPedidoFeitoList(list)
//                    pedidoViewModel.loadingProgressBar.value = false
//                }else{
//                    pedidoViewModel.loadingProgressBar.value = false
//                    Toast.makeText(requireContext(),"Sem pedidos",Toast.LENGTH_LONG).show()
//                }
//            }
//            .addOnFailureListener {
//                Log.i("teste","Falha")
//            }


    }

    fun startObservers(){
        pedidoViewModel.loadingProgressBar.observe(viewLifecycleOwner) {
            binding.pedidosProgressBar.visibility = if (it) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }
    }

    fun prepareRecyclerView() {
        adapter = PedidoFeitoAdapter {}
        binding.rvPedido.adapter = adapter
        binding.rvPedido.layoutManager = LinearLayoutManager(requireContext())
    }
    fun readPedidosFromUser(){

        db.collection("users")
            .document("clientes")
            .collection(auth.currentUser?.uid.toString())
            .document("pedidos")
            .collection("recentes")
            .get()
            .addOnSuccessListener{
                val list = ArrayList<Pedidos>()
                if(it.documents.isNotEmpty()){
                    for(i in it.documents){
                        if (i.exists()){
                            list.add(i.toObject(Pedidos::class.java)!!)
                        }else{
                            list.clear()
                        }
                    }
                    adapter.setPedidoFeitoList(list)
                    pedidoViewModel.loadingProgressBar.value = false
                }else{
                    pedidoViewModel.loadingProgressBar.value = false
                    Toast.makeText(requireContext(),"Sem pedidos",Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener {
                Log.i("teste","Falha")
            }

    }
}