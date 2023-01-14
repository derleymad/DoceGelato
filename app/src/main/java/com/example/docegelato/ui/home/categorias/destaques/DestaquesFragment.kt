package com.example.docegelato.ui.home.categorias.destaques

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docegelato.databinding.FragmentDestaquesBinding
import com.example.docegelato.ui.home.adapters.DestaqueAdapter
import com.squareup.picasso.Picasso

class DestaquesFragment : Fragment() {

    private var _binding: FragmentDestaquesBinding? = null
    private val binding get() = _binding!!
    private lateinit var destaqueAdapter : DestaqueAdapter

    private lateinit var viewModel: DestaquesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDestaquesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[DestaquesViewModel::class.java]
        viewModel.getDestaques()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        loadBanners()
        startObservers()
    }

    fun prepareRecyclerView(){
        destaqueAdapter = DestaqueAdapter()
        binding.rvDestaques.apply {
            adapter = destaqueAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }
    }
    fun loadBanners(){
        Picasso
            .get()
            .load("https://derleymad.github.io/DoceGelato/images/banner1.png")
            .into(binding.imgBannerMain)
        Picasso
            .get()
            .load("https://derleymad.github.io/DoceGelato/images/cupom.webp")
            .into(binding.imgBannerCupom)
    }

    fun startObservers(){
        viewModel.destaquesLiveData.observe(viewLifecycleOwner, Observer {
            destaqueAdapter.setDestaquesList(it)
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

















}