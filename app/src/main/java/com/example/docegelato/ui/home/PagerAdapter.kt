package com.example.docegelato.ui.home

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.docegelato.ui.home.categorias.bebidas.BebidasFragment
import com.example.docegelato.ui.home.categorias.destaques.DestaquesFragment
import com.example.docegelato.ui.home.categorias.entradas.EntradasFragment
import com.example.docegelato.ui.home.categorias.lanches.LanchesFragment
import com.example.docegelato.ui.home.categorias.montar.MontarFragment
import com.example.docegelato.ui.home.categorias.sobremesas.SobremesasFragment

class PagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                DestaquesFragment()
            }
            1 -> {
                EntradasFragment()
            }
            2 -> {
                LanchesFragment()
            }
            3 -> {
                SobremesasFragment()
            }
            4 -> {
                BebidasFragment()
            }
            5 -> {
                MontarFragment()
            }
            else -> {
                throw Resources.NotFoundException("Posição nao foi achada!")
            }
        }
    }

    override fun getItemCount(): Int {
        return 6
    }
}