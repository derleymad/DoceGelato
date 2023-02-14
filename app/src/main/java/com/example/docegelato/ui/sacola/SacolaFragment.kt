package com.example.docegelato.ui.sacola

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentSacolaBinding
import com.example.docegelato.ui.assets.BottomSheetEditFragment
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.util.Utils
import com.squareup.picasso.Picasso

class SacolaFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentSacolaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSacolaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startOnClickListeners()
        startObserver()
    }

    private fun startOnClickListeners() {
        binding.btnMinor.setOnClickListener {
            if (homeViewModel.quantityLiveData.value != 1) {
                homeViewModel.diminuirQuantidade()
            }
        }
        binding.addObs.setOnClickListener {
            BottomSheetEditFragment().show(requireActivity().supportFragmentManager, "Bottom sheet")
        }

        binding.btnPlus.setOnClickListener {
            homeViewModel.aumentarQuantidade()
        }
        binding.btnBackToBaseFragment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun startObserver() {
        homeViewModel.idLiveData.observe(viewLifecycleOwner) { id ->
            for (i in homeViewModel.categoriaLiveData.value!!) {
                for (j in i.comidas) {
                    if (j.comida_id == id) {
                        // SE A CATEGORIA FOR MONTE(O ID TEM NUMERO 5)
                        if(i.id == 5){
//                            binding.

                        }
                        binding.apply {
                            tvSacolaTitle.text = j.comida_title
                            tvSacolaDescricao.text = j.comida_desc
                            tvSacolaPreco.text = Utils.format(j.comida_preco!!)
                            Picasso.get().load(j.image).error(R.drawable.banner)
                                .placeholder(R.drawable.banner).into(imgSacola)
                            btnBottomAdicionar.setOnClickListener {
                                if (homeViewModel.address.value == null) {
                                }
                                homeViewModel.obsLiveData.value = tvObs.text.toString()
                                homeViewModel.setComidaToPedidos(
                                    j,
                                    homeViewModel.user.value!!,
                                    homeViewModel.address.value!!
                                )
                                requireActivity().findNavController(R.id.nav_host_fragment_activity_main)
                                    .popBackStack()
                                homeViewModel.isPedidoFeitoLiveData.value = true
                            }
                        }
                        homeViewModel.quantityLiveData.observe(viewLifecycleOwner) {
                            binding.editBottomQuantity.text =
                                homeViewModel.quantityLiveData.value.toString()
                            binding.btnBottomAdicionar.text = getString(
                                R.string.adicionar_pedido,
                                Utils.format(it.times(j.comida_preco!!))
                            )
                        }
                        homeViewModel.obsLiveData.observe(viewLifecycleOwner) {
                            binding.tvObs.text = it
                        }
                    } else {
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        homeViewModel.obsLiveData.value = ""
        homeViewModel.quantityLiveData.value = 1
        super.onDestroyView()
    }
}


