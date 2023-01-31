package com.example.docegelato.ui.pedido


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.example.docegelato.databinding.FragmentPedidoBinding
import com.example.docegelato.model.categorias.Pedidos
import com.example.docegelato.util.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PedidoFragment : Fragment() {

    private var _binding: FragmentPedidoBinding? = null
    private val binding get() = _binding!!
    private val pedidoViewModel: PedidoViewModel by activityViewModels()
    private lateinit var adapter: PedidoFeitoAdapter
    private var db = Firebase.firestore
    private var auth = FirebaseAuth.getInstance()
    private var data = ArrayList<Pedidos>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPedidoBinding.inflate(inflater, container, false)
        prepareRecyclerView()
        binding.rvPedido.addOnItemTouchListener(object : OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_MOVE ->
                        pedidoViewModel.criarBadge.value = false
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i("testing2",FirebaseUtils.getFirebaseLocation().toString())
        adapter.setPedidoFeitoList(FirebaseUtils.getFirebaseLocation())

        pedidoViewModel.loadingProgressBar.value = false

        pedidoViewModel.loadingProgressBar.observe(viewLifecycleOwner) {
            binding.pedidosProgressBar.visibility = if (it) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }

    }

    fun prepareRecyclerView() {
        adapter = PedidoFeitoAdapter {}
        binding.rvPedido.adapter = adapter
        binding.rvPedido.layoutManager = LinearLayoutManager(requireContext())
    }
}