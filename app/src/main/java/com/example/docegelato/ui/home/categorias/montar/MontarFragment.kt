package com.example.docegelato.ui.home.categorias.montar

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.docegelato.R

class MontarFragment : Fragment() {

    companion object {
        fun newInstance() = MontarFragment()
    }

    private lateinit var viewModel: MontarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_montar, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MontarViewModel::class.java)
        // TODO: Use the ViewModel
    }

}