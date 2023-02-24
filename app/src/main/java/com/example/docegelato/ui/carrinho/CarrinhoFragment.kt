package com.example.docegelato.ui.carrinho

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentCarrinhoBinding
import com.example.docegelato.extensions.navCarrinhoToMaps
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.ui.home.adapters.PedidoAdapter
import com.example.docegelato.util.Utils
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class CarrinhoFragment : Fragment() {

    private var _binding: FragmentCarrinhoBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterPedidos: PedidoAdapter
    var auth = FirebaseAuth.getInstance()
    var db = Firebase.firestore

    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarrinhoBinding.inflate(inflater, container, false)
        Log.i("callingresume","chamou")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        startObservers()
        startEventClickListeners()

    }

    private fun startEventClickListeners() {
        binding.btnBackToBaseFragment.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.localizacao.setOnClickListener {
            navCarrinhoToMaps()
        }

        binding.btnFinalizarTudo.setOnClickListener {
            addOnRecentePedidos()
        }
    }

    private fun showToast() {
        val toastLayout = layoutInflater.inflate(R.layout.custom_toast,requireActivity().findViewById(R.id.toast))
        Toast(requireContext()).apply {
            duration = Toast.LENGTH_LONG
            view = toastLayout
        }.show()
    }

    private fun addOnRecentePedidos() {
        homeViewModel.isLoadingContent.value = true
        val currentDateTime = Calendar.getInstance().timeInMillis
        homeViewModel.listPedidoFeitoLiveData.value?.id = currentDateTime
        val pedidos = homeViewModel.listPedidoFeitoLiveData.value?.pedidos
        if (pedidos?.isEmpty() == false) {
            db.collection("pedidos").document(currentDateTime.toString()).set(homeViewModel.listPedidoFeitoLiveData.value!!).addOnSuccessListener {
                homeViewModel.isPedidoFeitoLiveData.value = false
                homeViewModel.precoTotalLiveData.value = 0f
                homeViewModel.clearPedidosAndPrices()
                homeViewModel.isLoadingContent.value = false
                showToast()
                findNavController().popBackStack()
            }
        } else {
            Snackbar.make(
                binding.root,
                "Sem nada no carrinho para o pedido ser feito.",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun prepareRecyclerView() {
        adapterPedidos = PedidoAdapter {
            adapterPedidos.removePedidoAndUpdateRecyclerView(it)
            homeViewModel.removeComidaDosPedidos(it)
        }
        binding.rvPedidoFeito.adapter = adapterPedidos
        binding.rvPedidoFeito.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun startObservers() {
        homeViewModel.address.observe(viewLifecycleOwner){
            binding.localizacao.text = "${it.bairro+", " +it.rua+", "+it.numero_da_casa}"
        }
        homeViewModel.isLoadingContent.observe(viewLifecycleOwner){
            binding.progressBar.visibility = if(it)View.VISIBLE else View.GONE
        }
        homeViewModel.precoTotalLiveData.observe(viewLifecycleOwner) {
            binding.tvTotalPedido.text = Utils.format(it)
        }
        homeViewModel.listPedidoFeitoLiveData.observe(viewLifecycleOwner) {
            adapterPedidos.addPedidoToRecyclerViewList(it.pedidos!!)
        }
        homeViewModel.isPedidoFeitoLiveData.observe(viewLifecycleOwner) { it ->
            binding.apply {
                if (it) {
                    binding.included.root.visibility = View.INVISIBLE
                    lnBottomFinalizar.visibility = View.VISIBLE
                    rvPedidoFeito.visibility = View.VISIBLE
                    binding.containerLocalizacao.visibility = View.VISIBLE
                } else {
                    binding.included.root.visibility = View.VISIBLE
                    lnBottomFinalizar.visibility = View.INVISIBLE
                    rvPedidoFeito.visibility = View.INVISIBLE
                    binding.containerLocalizacao.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}