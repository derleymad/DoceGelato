package com.example.docegelato.ui.home.categorias.base

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docegelato.databinding.FragmentLanchesBinding
import com.example.docegelato.model.categorias.Comida
import com.example.docegelato.ui.home.adapters.ComidasAdapter
import com.example.docegelato.ui.home.HomeViewModel

class BaseFragment(private val tabNumber : Int) : Fragment() {

    private var _binding: FragmentLanchesBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var adapter: ComidasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLanchesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        homeViewModel.categoriaLiveData.observe(viewLifecycleOwner){
            adapter.setComidasList(it[tabNumber].comidas as ArrayList<Comida>)
        }
    }

    private fun prepareRecyclerView(){
        adapter = ComidasAdapter({ Log.i("clickou","clickou $it")})
        binding.rvLanches.adapter = adapter
        binding.rvLanches.layoutManager = LinearLayoutManager(requireContext())
    }

}