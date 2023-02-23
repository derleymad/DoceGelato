package com.example.docegelato.ui.sacola.adapter

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docegelato.R
import com.example.docegelato.model.categorias.Adicionais
import com.squareup.picasso.Picasso

class AdicionaisAdapter(
    private var adicionalPlusOnClickListener: ((String) -> Unit)? = null,
    private var adicionalMinorOnClickListener: ((String) -> Unit)? = null
): RecyclerView.Adapter<AdicionaisAdapter.ViewHolder>() {
    private val list = ArrayList<Adicionais>()

    @SuppressLint("NotifyDataSetChanged")
    fun addToRecyclerViewList(list: ArrayList<Adicionais>){
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adicionais,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(currentItem: Adicionais) {
            val adicional = itemView.findViewById<TextView>(R.id.nome_adicional)
            val btnPlus = itemView.findViewById<ImageButton>(R.id.btn_plus)
            val btnRemove = itemView.findViewById<ImageButton>(R.id.btn_remove)
            val image = itemView.findViewById<ImageView>(R.id.img_adicional)

            Picasso.get().load(currentItem.image).placeholder(R.drawable.placeholder).into(image)

            adicional.text = currentItem.nome
            btnPlus.setOnClickListener {
                adicionalPlusOnClickListener?.invoke(currentItem.nome!!)
                it.visibility = View.GONE
                btnRemove.visibility = View.VISIBLE

            }
            btnRemove.setOnClickListener {
                adicionalMinorOnClickListener?.invoke(currentItem.nome!!)
                it.visibility = View.GONE
                btnPlus.visibility = View.VISIBLE
            }
        }

    }

}