package com.example.docegelato.extensions

import android.os.Build
import android.text.Html
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.docegelato.R

private val navOptionsPushs = NavOptions.Builder()
    .setEnterAnim(R.anim.push_up_in)
    .setExitAnim(R.anim.push_up_out)
    .setPopEnterAnim(R.anim.push_down_in)
    .setPopExitAnim(R.anim.push_down_out)
    .build()

private val navOptionsPushsPerfil = NavOptions.Builder()
    .setEnterAnim(R.anim.push_up_in)
    .setExitAnim(R.anim.push_up_out)
    .setPopEnterAnim(R.anim.push_down_in)
    .setPopExitAnim(R.anim.push_down_out)
    .build()

private val navOptionsSlide = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .build()

fun NavController.navMainToCarrinho(destinationId: Int = 0) {
    this.navigate(R.id.action_mainFragment_to_carrinhoFragment, null, navOptionsPushs)
}

fun NavController.navMainToSacola(destinationId: Int = 0) {
    this.navigate(R.id.action_mainFragment_to_sacolaFragment, null, navOptionsSlide)
}

fun NavController.navMainToMaps(){
    this.navigate(R.id.action_mainFragment_to_mapsFragment2)
}

private fun NavController.navMainToPerfil(destinationId: Int = 0) {
    this.navigate(R.id.action_mainFragment_to_perfilFragment, null, navOptionsPushsPerfil)
}

private fun NavController.navMainToLocation(destinationId: Int = 0) {
    this.navigate(R.id.action_mainFragment_to_mapsFragment2, null, navOptionsPushsPerfil)
}

//private fun NavController.navPerfilToAdmin(destinationId: Int = 0) {
//    this.navigate(R.id.action_mainFragment_to_mapsFragment2, null, navOptionsPushsPerfil)
//}

fun NavController.navLoginToNewLocation(){
    this.navigate(R.id.action_loginFragment_to_mapsFragment,null, navOptionsSlide)
}

fun Fragment.navToMaps(){
    this.requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navMainToLocation()
}
fun Fragment.navToPerfil(){
    this.requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navMainToPerfil()
}

fun Fragment.navPerfilToAdmin(){
    this.requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_perfilFragment_to_adminFragment,null,navOptions = navOptionsSlide)
}

fun Fragment.navCarrinhoToMaps(){
    this.requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_carrinhoFragment_to_mapsFragment2,null,navOptions = navOptionsSlide)
}
fun Fragment.navSearchToSacola(){
    this.requireActivity().findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_mainFragment_to_sacolaFragment,null, navOptionsSlide)
}

fun Fragment.getFormattedString(myString : String) :  String{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(getString(R.string.usar_localiza_o_atual,myString),Html.FROM_HTML_MODE_LEGACY).toString()

    } else {
        return myString
    }
}