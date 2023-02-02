package com.example.docegelato.ui.login.createadress

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentMapsBinding
import com.example.docegelato.model.categorias.Address
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MapsFragment : Fragment() {
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private var db = Firebase.firestore
    private var auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSetOnClickListeners()
        checkIfValueFromViewModelIsNull()
    }

    private fun checkIfValueFromViewModelIsNull() {
    }

    private fun initSetOnClickListeners() {

        binding.btnBack.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment_activity_main).popBackStack()
        }

        binding.btnSave.setOnClickListener {
            if (binding.editBairro.text?.isEmpty() == true ||
                binding.editCasa.text?.isEmpty() == true ||
                binding.editRua.text?.isEmpty() == true ||
                binding.editPontoReferencia.text?.isEmpty() == true
            ) {
                Snackbar.make(
                    binding.root,
                    "Por favor preencha todos os campos!",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                db.collection("users")
                    .document("clientes")
                    .collection(auth.currentUser?.uid.toString())
                    .document("location")
                    .set(
                        Address(
                            binding.editBairro.text.toString(),
                            binding.editRua.text.toString(),
                            "kariús",
                            binding.editCasa.text.toString(),
                            binding.editPontoReferencia.text.toString(),
                        )
                    )
                    .addOnSuccessListener {
                        Log.i("tag","Gravou com sucesso")
                        Snackbar.make(binding.root,"Endereço salvo",Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                    .addOnFailureListener {
                        Log.i("tag","Gravou com erro")
                    }
            }
        }
    }
}