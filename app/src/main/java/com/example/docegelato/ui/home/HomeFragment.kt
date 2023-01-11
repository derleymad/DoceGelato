package com.example.docegelato.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.docegelato.databinding.FragmentHomeBinding
import com.example.docegelato.model.Comida
import com.example.docegelato.model.ComidaItem
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var comidaAdapter : ComidasAdapter
    private lateinit var homeViewModel : HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        prepareRecyclerView()
        homeViewModel.getComidas()

        //Obsevers
        homeViewModel.nomedaruaLiveData.observe(viewLifecycleOwner, Observer {
            binding.tvNomedarua.text = it
        })
        homeViewModel.comidaLiveData.observe(viewLifecycleOwner, Observer {
            comidaAdapter.setComidasList(it)
        })
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun prepareRecyclerView(){
        comidaAdapter = ComidasAdapter()
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = comidaAdapter
        }
    }
}