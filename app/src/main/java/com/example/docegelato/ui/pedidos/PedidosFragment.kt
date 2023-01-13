package com.example.docegelato.ui.pedidos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.docegelato.databinding.FragmentPedidosBinding

class PedidosFragment : Fragment() {

    private var _binding: FragmentPedidosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val pedidosViewModel =
            ViewModelProvider(this).get(PedidosViewModel::class.java)

        _binding = FragmentPedidosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        pedidosViewModel.text.observe(requireParentFragment().viewLifecycleOwner) {
            textView.text = it
            Log.i("itsworking",it)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("itsworking","pedidos,destruido")
        _binding = null
    }
}