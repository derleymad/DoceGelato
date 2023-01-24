package com.example.docegelato.ui.carrinho

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentCarrinhoBinding
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.ui.home.adapters.PedidoAdapter
import com.example.docegelato.util.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CarrinhoFragment : Fragment() {

    private var _binding: FragmentCarrinhoBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterPedidos : PedidoAdapter

    private val homeViewModel : HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarrinhoBinding.inflate(inflater, container, false)
//        findNavController().popBackStack(R.id.sacolaFragment, true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        startObservers()
        clearBadges()

        val database= Firebase.database

        binding.btnFinalizarTudo.setOnClickListener {
            val myRef = database.getReference("pedidos")
            val empId = myRef.push().key!!
            myRef.child(empId).setValue(homeViewModel.listPedidoFeitoLiveData.value)
        }
    }

    private fun clearBadges() {
        val navBar  = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        navBar.removeBadge(R.id.navigation_carrinho)
    }

    private fun prepareRecyclerView() {
        adapterPedidos = PedidoAdapter{
            homeViewModel.removeComidaDosPedidos(it)
            adapterPedidos.removePedidoAndUpdateRecyclerView(it)
        }
        binding.rvPedidoFeito.adapter = adapterPedidos
        binding.rvPedidoFeito.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun startObservers(){
        homeViewModel.precoTotalLiveData.observe(viewLifecycleOwner){
            binding.tvTotalPedido.text = Utils().format(it)
        }
        homeViewModel.listPedidoFeitoLiveData.observe(viewLifecycleOwner){
            adapterPedidos.addPedidoToRecyclerViewList(it.pedidos)
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