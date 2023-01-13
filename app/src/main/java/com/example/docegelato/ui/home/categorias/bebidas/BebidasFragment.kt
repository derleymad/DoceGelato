package com.example.docegelato.ui.home.categorias.bebidas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.docegelato.R

class BebidasFragment : Fragment() {

    companion object {
        fun newInstance() = BebidasFragment()
    }

    private lateinit var viewModel: BebidasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bebidas, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BebidasViewModel::class.java)
        // TODO: Use the ViewModel
    }

}