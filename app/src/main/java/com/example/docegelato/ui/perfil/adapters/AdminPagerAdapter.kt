package com.example.docegelato.ui.perfil.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.docegelato.ui.perfil.admin.BaseAdminFragment

class AdminPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {BaseAdminFragment(0)}
            1 -> {BaseAdminFragment(1)}
            2 -> {BaseAdminFragment(2)}

            else -> {
                throw Resources.NotFoundException("Posição nao foi achada!")
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}