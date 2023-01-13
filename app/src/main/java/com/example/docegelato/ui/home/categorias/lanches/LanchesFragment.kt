package com.example.docegelato.ui.home.categorias.lanches

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.docegelato.R

class LanchesFragment : Fragment() {

    companion object {
        fun newInstance() = LanchesFragment()
    }

    private lateinit var viewModel: LanchesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_salgados, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LanchesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}