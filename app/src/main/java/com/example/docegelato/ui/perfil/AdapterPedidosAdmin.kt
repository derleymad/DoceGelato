package com.example.docegelato.ui.perfil

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docegelato.R
import com.example.docegelato.model.categorias.Pedidos

class AdapterPedidosAdmin : RecyclerView.Adapter<AdapterPedidosAdmin.PedidosAdminViewholder>(){
    val list = ArrayList<Pedidos>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListToRecyclerView(list : ArrayList<Pedidos>){
        this.list.clear()
        this.list.addAll(
            list
        )
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidosAdminViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pedido_feito_item, parent, false)
        return PedidosAdminViewholder(view)
    }

    override fun onBindViewHolder(holder: PedidosAdminViewholder, position: Int) {
        val currentItem = list[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class PedidosAdminViewholder(view:View) : RecyclerView.ViewHolder(view){
        fun bind(currentItem : Pedidos){
            val text = itemView.findViewById<TextView>(R.id.tv_pedido_feito_nome_pessoa)
            text.text = currentItem.user?.nome
        }
    }
}