package com.example.docegelato.ui.pedido

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentPedidoBinding
import com.example.docegelato.ui.home.HomeViewModel

class PedidoFragment : Fragment() {

    private var _binding: FragmentPedidoBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel : HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPedidoBinding.inflate(inflater, container, false)
        return binding.root
    }

}