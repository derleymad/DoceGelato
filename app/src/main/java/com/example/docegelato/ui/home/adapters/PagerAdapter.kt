package com.example.docegelato.ui.home.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.docegelato.ui.home.categorias.destaques.DestaquesFragment
import com.example.docegelato.ui.home.categorias.base.BaseFragment

class PagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                DestaquesFragment()
            }
            1 -> {
//                EntradasFragment()
                BaseFragment(0)
            }
            2 -> {
                BaseFragment(1)
            }
            3 -> {
//                SobremesasFragment()
                BaseFragment(2)
            }
            4 -> {
//                BebidasFragment()
                BaseFragment(3)
            }
            5 -> {
//                MontarFragment()
                BaseFragment(4)
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