package com.example.docegelato.ui.sacola

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentSacolaBinding
import com.example.docegelato.ui.home.HomeViewModel
import com.squareup.picasso.Picasso

class SacolaFragment : Fragment() {

    private val homeViewModel : HomeViewModel by activityViewModels()
    private var _binding: FragmentSacolaBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
                        homeViewModel.actualComidaLiveData.value = j
                        binding.apply {
                            tvSacolaTitle.text = j.comida_title
                            tvSacolaDescricao.text = j.comida_desc
                            tvSacolaPreco.text = j.comida_preco
                            Picasso.get().load(j.image).error(R.drawable.banner).placeholder(R.drawable.banner).into(imgSacola)
                        }
                        homeViewModel.quantityLiveData.observe(viewLifecycleOwner){
                            Log.i("addorminor",it.toString())
                            binding.editBottomQuantity.text = homeViewModel.quantityLiveData.value.toString()
                            binding.btnBottomAdicionar.text = "Adicionar R$ ${it.toInt()*j.comida_preco.toFloat()}"
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
            if (liveData!=0){
                homeViewModel.quantityLiveData.value = homeViewModel.quantityLiveData.value?.minus(1)
            }
        }
        binding.btnPlus.setOnClickListener {
            homeViewModel.quantityLiveData.value = homeViewModel.quantityLiveData.value?.plus(1)
        }
    }
}


