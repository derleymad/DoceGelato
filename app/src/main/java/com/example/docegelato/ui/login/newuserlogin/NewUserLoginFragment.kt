package com.example.docegelato.ui.login.newuserlogin

import android.Manifest
import android.annotation.SuppressLint
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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.example.docegelato.R
import com.example.docegelato.databinding.FragmentHomeBinding
import com.example.docegelato.databinding.FragmentNewUserLoginBinding
import com.example.docegelato.extensions.getFormattedString
import com.example.docegelato.extensions.navNewUserToNewLocation
import com.example.docegelato.ui.home.HomeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

class NewUserLoginFragment : Fragment() {

    private var _binding: FragmentNewUserLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: NewUserLoginViewModel
    private val homeViewModel : HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewUserLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[NewUserLoginViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        homeViewModel.address.observe(viewLifecycleOwner){
            binding.tvCurrentLocation.text = getFormattedString(it.rua.toString())
        }

        binding.lnLocateAtual.setOnClickListener {
            getLocation()
        }
        binding.newAdress.setOnClickListener {
            findNavController().navNewUserToNewLocation()
        }
    }



    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(requireActivity()) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(
                                location.latitude,
                                location.longitude,
                                1
                            ) as List<Address>
                        val rua = list[0].thoroughfare.toString()
                        val adress = list[0].getAddressLine(0)
                        val cidade = list[0].subAdminArea.toString()
                        val estado = list[0].adminArea.toString()

                        homeViewModel.address.value =
                            com.example.docegelato.model.categorias.Address(
                                bairro = adress,
                                rua = rua,
                                cidade = cidade,
                                "13",
                                ponto_referencia = ""
                            )
                        homeViewModel.nomedaruaLiveData.value = "$cidade - $estado"

                    }
                }
            } else {
                Toast.makeText(requireContext(), "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            2
        )
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == 2) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            } else {
                Toast.makeText(
                    requireContext(),
                    "A localização é necessária para o uso do aplicativo!",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                requireContext(),
                "A localização é necessária para o uso do aplicativo!",
                Toast.LENGTH_LONG
            ).show()
        }
    }






}