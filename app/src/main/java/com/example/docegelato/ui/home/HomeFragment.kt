package com.example.docegelato.ui.home

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentHomeBinding
import com.example.docegelato.model.categorias.User
import com.example.docegelato.ui.home.adapters.PagerAdapter
import com.example.docegelato.ui.pedido.PedidoViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel : HomeViewModel by activityViewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupTabLayout()
        homeViewModel.getCategorias()
        Picasso.get().load(homeViewModel.user.value?.imagemPerfil.toString()).placeholder(R.drawable.placeholder).into(binding.imgPerfil)
        startObservers()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupTabLayout() {
        binding.apply {
            viewPager.adapter =
                PagerAdapter(requireActivity())
            viewPager.offscreenPageLimit = 6
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Destaques"
                    1 -> "Entradas"
                    2 -> "Lanches"
                    3 -> "Sobremesas"
                    4 -> "Bebidas"
                    5 -> "Monte o seu"
                    else -> throw  Resources.NotFoundException("Posição não encontrada!")
                }
            }.attach()
        }
    }

    private fun startObservers(){
        homeViewModel.nomedaruaLiveData.observe(viewLifecycleOwner, Observer {
            binding.btnExpandmore.text = it
        })

        homeViewModel.isLoadingContent.observe(viewLifecycleOwner){
            binding.homeProgressBar.visibility = if(it)View.VISIBLE else View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}