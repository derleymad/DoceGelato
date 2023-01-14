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

class DestaqueAdapter(
//    @LayoutRes private var layoutItem: Int,
    private var movieOnClickListener: ((Int) -> Unit)? = null
): RecyclerView.Adapter<DestaqueAdapter.ComidasViewHolder>() {
    private var comidasList = ArrayList<Comida>()

    fun setDestaquesList(comidasList: ArrayList<Comida>){
        this.comidasList= comidasList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comida_destaque_item,parent,false)
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
            val nome = itemView.findViewById<TextView>(R.id.tv_title_comida_destaque)
            val preco = itemView.findViewById<TextView>(R.id.tv_preco_destaque)
            val imageView = itemView.findViewById<ImageView>(R.id.img_comida_destaque)

            Picasso
                .get()
                .load("${comida.image}")
                .error(R.drawable.banner)
                .placeholder(R.drawable.banner)
                .into(imageView);

            nome.text = comida.comida_title
            preco.text = comida.comida_preco

        }
    }
}