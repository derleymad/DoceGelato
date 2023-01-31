package com.example.docegelato.ui.login.fragments.login

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.example.docegelato.MainActivity
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import java.util.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        changeGoogleBtnName()

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("772908591323-4t4sqne3bkd8h9pudf1uttgj944ga712.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        if (auth.currentUser?.email != null) {
            startActivity(Intent(requireActivity(),MainActivity::class.java))
            requireActivity().finish()
        } else {
            googleSignInClient.signOut()
        }
        binding.btnGoogleEntrar.setOnClickListener {
            signInGoogle()
        }

    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleResults(task)
            }
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result

            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(requireContext(), "Handle fail", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {

                if(it.result.additionalUserInfo?.isNewUser == true){
                    findNavController().navigate(
                        R.id.mapsFragment,
                    )
                }else{
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            } else {
                Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun changeGoogleBtnName() {
        var btnText = binding.btnGoogleEntrar.getChildAt(0) as TextView
        btnText.text = "Fazer login com a conta do google"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }







}