package com.example.docegelato.ui.sacola

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentSacolaBinding
import com.example.docegelato.ui.home.HomeViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class SacolaFragment : Fragment() {

    private val homeViewModel : HomeViewModel by activityViewModels()
    private var _binding: FragmentSacolaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSacolaBinding.inflate(inflater, container, false)
        homeViewModel.idLiveData.observe(viewLifecycleOwner){ id->
            for (i in homeViewModel.categoriaLiveData.value!!){
                for (j in i.comidas){
                    if (j.comida_id==id){
                        binding.apply {
                            tvSacolaTitle.text = j.comida_title
                            tvSacolaDescricao.text = j.comida_desc
                            tvSacolaPreco.text = j.comida_preco.toString()
                            btnBottomAdicionar.setOnClickListener {
                                homeViewModel.obsLiveData.value = editObservacao.text.toString()
                                homeViewModel.isPedidoFeitoLiveData.value = true
                                homeViewModel.setComidaToPedidos(j)
                                var snack = Snackbar.make(binding.root, "${j.comida_title} Adicionado aos pedidos", Snackbar.ANIMATION_MODE_SLIDE)
                                snack.setAnchorView(R.id.bottom_adicionar).show()

                            }
                            Picasso.get().load(j.image).error(R.drawable.banner).placeholder(R.drawable.banner).into(imgSacola)
                        }
                        homeViewModel.quantityLiveData.observe(viewLifecycleOwner){
                            binding.editBottomQuantity.text = homeViewModel.quantityLiveData.value.toString()
                            binding.btnBottomAdicionar.text = "Adicionar R$ ${it.times(j.comida_preco!!)}"
                        }
                    }else{}
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnMinor.setOnClickListener {
            var liveData = homeViewModel.quantityLiveData.value
            if (liveData!=1){
                homeViewModel.quantityLiveData.value = homeViewModel.quantityLiveData.value?.minus(1)
            }
        }
        startObserver()
        binding.btnPlus.setOnClickListener {
            homeViewModel.quantityLiveData.value = homeViewModel.quantityLiveData.value?.plus(1)
        }
        binding.btnBackToBaseFragment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun startObserver(){
    }

    override fun onDestroyView() {
        homeViewModel.obsLiveData.value = ""
        homeViewModel.quantityLiveData.value = 1
        super.onDestroyView()
    }
}


