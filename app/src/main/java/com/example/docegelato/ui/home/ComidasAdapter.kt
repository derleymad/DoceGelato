package com.example.docegelato.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.docegelato.R
import com.example.docegelato.model.categorias.Comida
import com.squareup.picasso.Picasso

class ComidasAdapter(
    private val comidasList: List<Comida>,
    @LayoutRes private var layoutItem: Int,
    private var movieOnClickListener: ((Int) -> Unit)? = null
): RecyclerView.Adapter<ComidasAdapter.ComidasViewHolder>() {

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
        fun bind(comida : Comida){
            val nome = itemView.findViewById<TextView>(R.id.nome)
            val preco = itemView.findViewById<TextView>(R.id.preco)
            val desc = itemView.findViewById<TextView>(R.id.descricao)
            val imageView = itemView.findViewById<ImageView>(R.id.foto)

            Picasso
                .get()
                .load("https://raw.githubusercontent.com/derleymad/DoceGelato/api/${comida.image}")
                .error(R.drawable.banner)
                .placeholder(R.drawable.banner)
                .into(imageView);

            nome.text = comida.comida_title
            desc.text = comida.comida_desc
            preco.text = comida.comida_preco

        }
    }
}