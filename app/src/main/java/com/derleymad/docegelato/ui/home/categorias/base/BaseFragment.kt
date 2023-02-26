package com.derleymad.docegelato.ui.home.categorias.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.derleymad.docegelato.databinding.FragmentBaseBinding
import com.derleymad.docegelato.extensions.navMainToSacola
import com.derleymad.docegelato.model.categorias.Comida
import com.derleymad.docegelato.ui.home.HomeViewModel
import com.derleymad.docegelato.ui.home.adapters.ComidasAdapter

//TODO FIX THIS CONSTRUTOR
class BaseFragment(val tabNumber: Int=0) : Fragment() {

    private var _binding: FragmentBaseBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var adapter: ComidasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        homeViewModel.categoriaLiveData.observe(viewLifecycleOwner) {
            adapter.setComidasList(it[tabNumber].comidas as ArrayList<Comida>)
        }
    }

    private fun prepareRecyclerView() {
        adapter = ComidasAdapter {
            findNavController().navMainToSacola()
            homeViewModel.nameLiveData.value = it
        }
        binding.rvLanches.adapter = adapter
        binding.rvLanches.layoutManager = LinearLayoutManager(requireContext())
    }

}