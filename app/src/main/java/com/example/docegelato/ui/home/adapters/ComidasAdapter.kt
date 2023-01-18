package com.example.docegelato.ui.home.adapters

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
import kotlin.collections.ArrayList

class ComidasAdapter(
    private var comidaOnClickListener: ((Int) -> Unit)? = null
): RecyclerView.Adapter<ComidasAdapter.ComidasViewHolder>() {

    private var comidasList = ArrayList<Comida>()

    fun setComidasList(comidasList: ArrayList<Comida>){
        this.comidasList= comidasList
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

    inner class ComidasViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(comida : Comida){
            val nome = itemView.findViewById<TextView>(R.id.nome)
            val preco = itemView.findViewById<TextView>(R.id.preco)
            val desc = itemView.findViewById<TextView>(R.id.descricao)
            val imageView = itemView.findViewById<ImageView>(R.id.foto)

            val format = NumberFormat.getCurrencyInstance(Locale("pt-br", "br"))
            Picasso
                .get()
                .load("${comida.image}")
                .error(R.drawable.banner)
                .placeholder(R.drawable.banner)
                .into(imageView);

            nome.text = comida.comida_title
            desc.text = comida.comida_desc
            preco.text =  if(comida.comida_preco!=null) format.format(comida.comida_preco ) else "Preço por tamanho ou porção"

            itemView.setOnClickListener {
                comidaOnClickListener?.invoke(comida.comida_id)
            }

        }
    }
}