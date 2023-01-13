package com.example.docegelato.ui.home

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel : HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        homeViewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupTabLayout()
        //Obsevers
        homeViewModel.nomedaruaLiveData.observe(viewLifecycleOwner, Observer {
            binding.tvNomedarua.text = it
        })
//        homeViewModel.categoriaLiveData.observe(viewLifecycleOwner, Observer {
//            categoriasAdapter.setCategoriasList(it)
//        })
//
//        homeViewModel.destaquesLiveData.observe(viewLifecycleOwner){
//            destaquesAdapter.setDestaquesList(it)
//        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupTabLayout() {
        binding.apply {
            viewPager.adapter = PagerAdapter(requireActivity())
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
    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("itsworking","destruiu")
        _binding = null
    }
}