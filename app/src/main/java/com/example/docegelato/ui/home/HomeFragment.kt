package com.example.docegelato.ui.home

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentHomeBinding
import com.example.docegelato.ui.home.adapters.PagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
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
        startObservers()
        loadImgPerfil()
        startEventOnClickListeners()

        if(homeViewModel.isPedidoFeitoLiveData.value == true) {
            requireActivity().findViewById<CardView>(R.id.ln_carrinho_flutuante).visibility =
                View.VISIBLE
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadImgPerfil(){
        Picasso.get().load(homeViewModel.user.value?.imagemPerfil.toString()).placeholder(R.drawable.placeholder).into(binding.imgPerfil)
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

    private fun startEventOnClickListeners(){
        binding.editSearch.setOnClickListener {
            val item = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).menu.findItem(R.id.navigation_search)
            NavigationUI.onNavDestinationSelected(item, findNavController())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}