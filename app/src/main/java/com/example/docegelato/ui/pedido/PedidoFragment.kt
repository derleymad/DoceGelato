package com.example.docegelato.ui.pedido

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docegelato.databinding.FragmentPedidoBinding
import com.example.docegelato.model.pedidos.Pedidos
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObservers()
        //TODO LEMBRAR DE COLOCAR PARA ARRUMAR A BADGE QUANDO ENTRAR NO FRAGMENTO DOS PEDIDOS
        pedidoViewModel.criarBadge.value = false
    }

    fun startObservers(){
        pedidoViewModel.dataRequest.observe(viewLifecycleOwner){
            adapter.setPedidoFeitoList(it)
        }
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
}