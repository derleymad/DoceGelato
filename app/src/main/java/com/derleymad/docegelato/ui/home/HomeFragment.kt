package com.derleymad.docegelato.ui.home

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.derleymad.docegelato.R
import com.derleymad.docegelato.databinding.FragmentHomeBinding
import com.derleymad.docegelato.extensions.navToMaps
import com.derleymad.docegelato.extensions.navToPerfil
import com.derleymad.docegelato.ui.home.adapters.PagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()
        startObservers()
        loadImgPerfil()
        startEventOnClickListeners()
    }

    private fun loadImgPerfil() {
        Picasso.get().load(homeViewModel.user.value?.imagemPerfil.toString())
            .placeholder(R.drawable.placeholder).into(binding.imgPerfil)
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

    private fun startObservers() {
        homeViewModel.address.observe(viewLifecycleOwner){
            binding.btnExpandmore.text = it.rua
        }
    }

    private fun startEventOnClickListeners() {
        binding.editSearch.setOnClickListener {
            val item =
                requireActivity().findViewById<BottomNavigationView>(R.id.nav_view).menu.findItem(R.id.navigation_search)
            NavigationUI.onNavDestinationSelected(item, findNavController())
        }
        binding.btnExpandmore.setOnClickListener {
            navToMaps()
        }
        binding.imgPerfil.setOnClickListener {
            navToPerfil()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}