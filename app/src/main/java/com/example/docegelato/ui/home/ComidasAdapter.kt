package com.example.docegelato.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docegelato.R
import com.example.docegelato.model.Comida
import com.example.docegelato.model.ComidaItem

class ComidasAdapter: RecyclerView.Adapter<ComidasAdapter.ComidasViewHolder>() {
    private var comidasList = mutableListOf<ComidaItem>()

    fun setComidasList(comidasList: MutableList<ComidaItem>){
        this.comidasList = comidasList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comida_item,parent,false)
        return ComidasViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComidasViewHolder, position: Int) {
        val itemCurrent = comidasList[position]
        holder.bind(itemCurrent)
    }

    override fun getItemCount(): Int {
        return comidasList.size
    }

    class ComidasViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(comida : ComidaItem){
            val nome = itemView.findViewById<TextView>(R.id.nome)
            val preco = itemView.findViewById<TextView>(R.id.preco)
            nome.text = comida.title
            preco.text = comida.preco
        }
    }
}