package com.example.docegelato.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docegelato.R
import com.example.docegelato.model.categorias.Comida
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*

class SearchAdapter(
    private var comidaOnClickListener: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<SearchAdapter.ComidasViewHolder>() {

    private var comidasList = ArrayList<Comida>()

    fun setComidasList(comidasList: ArrayList<Comida>) {
        this.comidasList = comidasList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search, parent, false)
        return ComidasViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComidasViewHolder, position: Int) {
        val itemCurrent = comidasList[position]
        holder.bind(itemCurrent)
    }

    override fun getItemCount(): Int {
        return comidasList.size
    }

    inner class ComidasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(comida: Comida) {
            val format = NumberFormat.getCurrencyInstance(Locale("pt-br", "br"))
            val search = itemView.findViewById<TextView>(R.id.tv_search_comida)
            search.text = comida.comida_title
//            itemView.setOnClickListener {
//                comidaOnClickListener?.invoke(comida.comida_id)
//            }

        }
    }

}