package com.example.docegelato.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docegelato.databinding.FragmentHomeBinding
import com.example.docegelato.model.Comida
import com.example.docegelato.model.ComidaItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var comidaAdapter : ComidasAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        prepareRecyclerView()
        homeViewModel.getComidas()
        homeViewModel.movieLiveData.observe(viewLifecycleOwner, Observer {
            comidaAdapter.setComidasList(it)
        })
//        homeViewModel.comidas.observe(viewLifecycleOwner){
//            binding.rvHome.adapter = ComidasAdapter(it)
//            binding.rvHome.layoutManager = LinearLayoutManager(view?.context ?: null)
//        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun prepareRecyclerView(){
        comidaAdapter = ComidasAdapter()
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}