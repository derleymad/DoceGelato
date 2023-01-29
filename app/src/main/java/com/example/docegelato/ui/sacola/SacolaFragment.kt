package com.example.docegelato.ui.sacola

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentSacolaBinding
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.util.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso

class SacolaFragment : Fragment() {

    private val homeViewModel : HomeViewModel by activityViewModels()
    private var _binding: FragmentSacolaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        this.requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.GONE
        homeViewModel.hideNavBar.value = true
            requireActivity().findViewById<CardView>(R.id.ln_carrinho_flutuante).visibility =
                View.GONE
        // Inflate the layout for this fragment
        _binding = FragmentSacolaBinding.inflate(inflater, container, false)
        homeViewModel.idLiveData.observe(viewLifecycleOwner){ id->
            for (i in homeViewModel.categoriaLiveData.value!!){
                for (j in i.comidas){
                    if (j.comida_id==id){
                        binding.apply {
                            tvSacolaTitle.text = j.comida_title
                            tvSacolaDescricao.text = j.comida_desc
                            tvSacolaPreco.text = Utils().format(j.comida_preco!!)
                            Picasso.get().load(j.image).error(R.drawable.banner).placeholder(R.drawable.banner).into(imgSacola)
                            btnBottomAdicionar.setOnClickListener {
                                if(homeViewModel.address.value==null){
                                }
                                homeViewModel.obsLiveData.value = editObservacao.text.toString()
                                homeViewModel.isPedidoFeitoLiveData.value = true

                                homeViewModel.setComidaToPedidos(j, homeViewModel.user.value!!,homeViewModel.address.value!!)
                                findNavController().popBackStack()
                            }
                        }
                        homeViewModel.quantityLiveData.observe(viewLifecycleOwner){
                            binding.editBottomQuantity.text = homeViewModel.quantityLiveData.value.toString()
                            binding.btnBottomAdicionar.text = getString(R.string.adicionar_pedido,Utils().format(it.times(j.comida_preco!!)))
                        }
                    }else{}
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startOnClickListeners()
//        requireActivity().findViewById<CardView>(R.id.ln_carrinho_flutuante).visibility = View.GONE
//        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }

    private fun startOnClickListeners(){
        binding.btnMinor.setOnClickListener {
            if (homeViewModel.quantityLiveData.value!=1){
                homeViewModel.diminuirQuantidade()
            }
        }
        binding.btnPlus.setOnClickListener {
            homeViewModel.aumentarQuantidade()
        }
        binding.btnBackToBaseFragment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        homeViewModel.obsLiveData.value = ""
        homeViewModel.quantityLiveData.value = 1
        homeViewModel.hideNavBar.value = false
        super.onDestroyView()
    }
}


