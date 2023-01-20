package com.example.docegelato.ui.pedidos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docegelato.databinding.FragmentPedidosBinding
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.ui.home.adapters.PedidoAdapter
import com.example.docegelato.util.Utils

class PedidosFragment : Fragment() {

    private var _binding: FragmentPedidosBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterPedidos : PedidoAdapter

    private val homeViewModel : HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPedidosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        startObservers()
    }

    private fun prepareRecyclerView() {
        adapterPedidos = PedidoAdapter()
        binding.rvPedidoFeito.adapter = adapterPedidos
        binding.rvPedidoFeito.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun startObservers(){
        homeViewModel.listPedidoFeitoLiveData.observe(viewLifecycleOwner){
            adapterPedidos.addPedidoToRecyclerViewList(it.pedidos)
            binding.tvTotalPedido.text = Utils().format(it.preco_total)
        }

        homeViewModel.isPedidoFeitoLiveData.observe(viewLifecycleOwner){ it ->
            if(it){
                binding.lnImgCenter.visibility = View.INVISIBLE
                binding.lnBottomFinalizar.visibility = View.VISIBLE
            }else{
                binding.lnImgCenter.visibility = View.VISIBLE
                binding.lnBottomFinalizar.visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}