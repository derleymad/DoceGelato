package com.example.docegelato.ui.carrinho

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentCarrinhoBinding
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.ui.home.adapters.PedidoAdapter
import com.example.docegelato.util.Utils
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CarrinhoFragment : Fragment() {

    private var _binding: FragmentCarrinhoBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapterPedidos: PedidoAdapter
    var auth = FirebaseAuth.getInstance()
    var db = Firebase.firestore

    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.hideNavBar.value = true
        homeViewModel.hideCarrinhoFlutuante.value = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarrinhoBinding.inflate(inflater, container, false)
//        requireActivity().findViewById<CardView>(R.id.ln_carrinho_flutuante).visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        startObservers()
        startEventClickListeners()
        val database = Firebase.database

        binding.btnFinalizarTudo.setOnClickListener {
            if (homeViewModel.listPedidoFeitoLiveData.value?.pedidos?.isEmpty() == false) {
                val myUid = homeViewModel.auth.currentUser?.uid!!.toString()
                val myRefUsers = database.getReference("users")
                val empId = myRefUsers.push().key!!
                myRefUsers.child(myUid).child(empId)
                    .setValue(homeViewModel.listPedidoFeitoLiveData.value)
                homeViewModel.isPedidoFeitoLiveData.value = false
                homeViewModel.precoTotalLiveData.value = 0f
                homeViewModel.clearPedidosAndPrices()
            } else {
                Snackbar.make(
                    binding.root,
                    "Sem nada no carrinho para o pedido ser feito.",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }


    private fun startEventClickListeners() {
        binding.btnBackToBaseFragment.setOnClickListener {
            findNavController().popBackStack()
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
        homeViewModel.precoTotalLiveData.observe(viewLifecycleOwner) {
            binding.tvTotalPedido.text = Utils.format(it)
        }
        homeViewModel.listPedidoFeitoLiveData.observe(viewLifecycleOwner) {
            adapterPedidos.addPedidoToRecyclerViewList(it.pedidos!!)

            db.collection("users")
                .document("clientes")
                .collection(auth.currentUser?.uid.toString())
                .document("pedidos")
                .collection("recente")
                .add(it)
                .addOnSuccessListener{
                    Log.i("teste","Sucesso")
                }
                .addOnFailureListener {
                    Log.i("teste","Falha")
                }


        }
        homeViewModel.isPedidoFeitoLiveData.observe(viewLifecycleOwner) { it ->
            binding.apply {
                if (it) {
                    lnImgCenter.visibility = View.INVISIBLE
                    lnBottomFinalizar.visibility = View.VISIBLE
                    rvPedidoFeito.visibility = View.VISIBLE
                } else {
                    lnImgCenter.visibility = View.VISIBLE
                    lnBottomFinalizar.visibility = View.INVISIBLE
                    rvPedidoFeito.visibility = View.INVISIBLE
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        homeViewModel.hideNavBar.value = false
        homeViewModel.checkSeTemPedidoParaEsconderOuMostarCarrinhoFlutuante()
        _binding = null
    }
}