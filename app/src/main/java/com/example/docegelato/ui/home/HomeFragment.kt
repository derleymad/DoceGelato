package com.example.docegelato.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoriasAdapter : CategoryAdapter
    private lateinit var homeViewModel : HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        prepareRecyclerView()
        loadBanners()
        homeViewModel.getCategorias()
//        homeViewModel.getComidas()

        //Obsevers
        homeViewModel.nomedaruaLiveData.observe(viewLifecycleOwner, Observer {
            binding.tvNomedarua.text = it
        })
        homeViewModel.categoriaLiveData.observe(viewLifecycleOwner, Observer {
            categoriasAdapter.setCategoriasList(it)
        })
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun prepareRecyclerView(){
        categoriasAdapter = CategoryAdapter({Log.i("testeclick","clickou")})
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = categoriasAdapter
        }
    }

    private fun loadBanners(){
        Picasso.get().load("https://derleymad.github.io/DoceGelato/images/banner1.png").into(binding.imgBannerMain)
    }
}