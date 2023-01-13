package com.example.docegelato.ui.home.categorias.sobremesas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.docegelato.R

class SobremesasFragment : Fragment() {

    companion object {
        fun newInstance() = SobremesasFragment()
    }

    private lateinit var viewModel: SobremesasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sobremesas, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SobremesasViewModel::class.java)
        // TODO: Use the ViewModel
    }

}