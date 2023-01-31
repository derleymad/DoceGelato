package com.example.docegelato.extensions

import android.os.Build
import android.text.Html
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
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
fun NavController.navHomeToCarrinho(destinationId: Int = 0) {

    this.navigate(R.id.navigation_carrinho, null, navOptionsPushs)
}

fun NavController.navComidaToSacola(destinationId: Int = 0) {
    this.navigate(R.id.action_navigation_home_to_sacolaFragment, null, navOptionsSlide)
}

fun NavController.navHomeToPerfil(destinationId: Int = 0) {
    this.navigate(R.id.perfilFragment, null, navOptionsPushsPerfil)
}

fun NavController.navNewUserToNewLocation(){
    this.navigate(R.id.action_newUserLoginFragment_to_mapsFragment,null, navOptionsSlide)
}

fun Fragment.getFormattedString(myString : String) :  String{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(getString(R.string.usar_localiza_o_atual,myString),Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        return myString
    }
}