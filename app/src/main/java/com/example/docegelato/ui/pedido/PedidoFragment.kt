package com.example.docegelato.ui.pedido

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docegelato.databinding.FragmentPedidoBinding

class PedidoFragment : Fragment() {

    private var _binding: FragmentPedidoBinding? = null
    private val binding get() = _binding!!
    private val pedidoViewModel: PedidoViewModel by activityViewModels()
    private lateinit var adapter: PedidoFeitoAdapter

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
        pedidoViewModel.criarBadge.value = false
    }

    fun startObservers(){
        pedidoViewModel.dataRequest.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                binding.included.root.visibility = View.GONE
            }else{
                binding.included.root.visibility = View.VISIBLE
            }
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
        val layout = LinearLayoutManager(requireContext())
        layout.reverseLayout= true
        layout.stackFromEnd = true
        binding.rvPedido.layoutManager = layout
    }
}