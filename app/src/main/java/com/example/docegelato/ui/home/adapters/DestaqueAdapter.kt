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
import java.text.Format
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt
import kotlin.math.roundToLong

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
            val precoAntigo = itemView.findViewById<TextView>(R.id.tv_preco_antigo)
            val imageView = itemView.findViewById<ImageView>(R.id.img_comida_destaque)
            val desconto = itemView.findViewById<TextView>(R.id.tv_desconto)


            val format = NumberFormat.getCurrencyInstance(Locale("pt-br", "br"))

            val precoDescontado  = (comida.comida_preco)?.minus((comida.comida_desconto* comida.comida_preco!!))
            format.format(precoDescontado)

            Picasso
                .get()
                .load("${comida.image}")
                .error(R.drawable.banner)
                .placeholder(R.drawable.banner)
                .into(imageView);

            nome.text = comida.comida_title
            desconto.text = "${(comida.comida_desconto*100).toInt()}%"
            precoAntigo.text = itemView.context.getString(R.string.preco_riscado,format.format(comida.comida_preco))
            preco.text = itemView.context.getString(R.string.comida_destaque_preco,format.format(precoDescontado))


        }
    }
}