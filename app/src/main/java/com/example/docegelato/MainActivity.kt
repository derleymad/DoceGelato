package com.example.docegelato

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.docegelato.databinding.ActivityMainBinding
import com.example.docegelato.extensions.navHomeToCarrinho
import com.example.docegelato.ui.home.HomeViewModel
import com.example.docegelato.ui.pedido.PedidoViewModel
import com.example.docegelato.util.Utils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var homeViewModel : HomeViewModel
    private val permissionId = 2
    val pedidoViewModel : PedidoViewModel by viewModels()



    //FIREBASE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
        getLocation()
        pedidoViewModel.setListenerFirebaseEvent()
        startObservers()

        homeViewModel.getDestaques()
        homeViewModel.getCategorias()

        binding.lnCarrinhoFlutuante.setOnClickListener {
            navController.navHomeToCarrinho()
            binding.lnCarrinhoFlutuante.visibility = View.GONE
        }

        homeViewModel.isPedidoFeitoLiveData.observe(this){
            if (it){
                binding.lnCarrinhoFlutuante.visibility = View.VISIBLE
            }else{
                binding.lnCarrinhoFlutuante.visibility = View.GONE
            }
        }
    }
    private fun createOrRemoveBadge(criar: Boolean){
        val navBar: BottomNavigationView = findViewById(R.id.nav_view)
        when(criar){
            true -> navBar.getOrCreateBadge(R.id.navigation_pedido).number++
            else->navBar.removeBadge(R.id.navigation_pedido)
        }
    }

    private fun startObservers(){
        homeViewModel.hideNavBar.observe(this){
            binding.navView.visibility = if(it) View.GONE else View.VISIBLE
        }

        homeViewModel.precoTotalLiveData.observe(this){
            binding.totalPriceCarrinhoFlutuante.text = Utils().format(it)
        }
        pedidoViewModel.criarBadge.observe(this){
            createOrRemoveBadge(it)
        }
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1) as List<Address>
                        val rua = list[0].thoroughfare.toString()
                        val adress = list[0].getAddressLine(0)
                        val cidade = list[0].subAdminArea.toString()
                        val estado = list[0].adminArea.toString()

                        homeViewModel.address.value = com.example.docegelato.model.categorias.Address(bairro = adress, rua = rua , cidade = cidade,"13", ponto_referencia = "")
                        homeViewModel.nomedaruaLiveData.value = "$cidade - $estado"

                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }
    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }else{
                Toast.makeText(this,"A localização é necessária para o uso do aplicativo!",Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this,"A localização é necessária para o uso do aplicativo!",Toast.LENGTH_LONG).show()
        }
    }
}