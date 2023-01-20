package com.example.docegelato.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docegelato.R
import com.example.docegelato.model.categorias.Pedido
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*

class PedidoAdapter(): RecyclerView.Adapter<PedidoAdapter.ComidasViewHolder>() {
    private var pedidoList = mutableListOf<Pedido>()

    fun addPedidoToRecyclerViewList(pedidos: MutableList<Pedido>){
        this.pedidoList.addAll(pedidos)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pedido_item,parent,false)
        return ComidasViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComidasViewHolder, position: Int) {

        val itemCurrent = pedidoList[position]
        holder.bind(itemCurrent)
    }

    override fun getItemCount(): Int {
        return pedidoList.size
    }

    class ComidasViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(pedido : Pedido){
            val nome = itemView.findViewById<TextView>(R.id.tv_pedido_title)
            val quantity = itemView.findViewById<TextView>(R.id.tv_pedido_quantity)
            val imageView = itemView.findViewById<ImageView>(R.id.img_pedido)
            val obs = itemView.findViewById<TextView>(R.id.tv_obs)
            val price = itemView.findViewById<TextView>(R.id.preco)

            val format = NumberFormat.getCurrencyInstance(Locale("pt-br", "br"))

            Picasso
                .get()
                .load("${pedido.image}")
                .error(R.drawable.banner)
                .placeholder(R.drawable.banner)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView);

            nome.text = pedido.comida_title
            quantity.text = pedido.quantity.toString()
            obs.text = pedido.obs
            price.text = format.format(pedido.comida_preco)

        }
    }
}
