package com.example.docegelato.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docegelato.R
import com.example.docegelato.model.categorias.Pedido
import com.example.docegelato.util.Utils
import com.squareup.picasso.Picasso

class PedidoAdapter(
//    @LayoutRes private var layoutItem: Int? = null,
    private var esconderBtnRemover: Boolean = false,
    private var pedidoOnClickRemoveListener: ((Pedido) -> Unit)? = null
) : RecyclerView.Adapter<PedidoAdapter.ComidasViewHolder>() {
    private var pedidoList = mutableListOf<Pedido>()

    fun addPedidoToRecyclerViewList(pedidos: MutableList<Pedido>) {
        this.pedidoList.addAll(pedidos)
        notifyDataSetChanged()
    }

    fun removePedidoAndUpdateRecyclerView(pedido: Pedido) {
        val index = pedidoList.indexOf(pedido)
        pedidoList.remove(pedido)
        notifyItemRemoved(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pedido_item, parent, false)
        return ComidasViewHolder(view)

    }

    override fun onBindViewHolder(holder: ComidasViewHolder, position: Int) {
        val itemCurrent = pedidoList[position]
        holder.bind(itemCurrent)
    }

    override fun getItemCount(): Int {
        return pedidoList.size
    }

    inner class ComidasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pedido: Pedido) {
            val nome = itemView.findViewById<TextView>(R.id.tv_pedido_title)
            val quantity = itemView.findViewById<TextView>(R.id.tv_pedido_quantity)
            val imageView = itemView.findViewById<ImageView>(R.id.img_pedido)
            val obs = itemView.findViewById<TextView>(R.id.tv_obs)
            val price = itemView.findViewById<TextView>(R.id.preco)

            if (esconderBtnRemover) {
                val remove = itemView.findViewById<ImageButton>(R.id.btn_remove_pedido)
                remove.visibility = View.INVISIBLE

            } else {
                val remove = itemView.findViewById<ImageButton>(R.id.btn_remove_pedido)
                remove.setOnClickListener {
                    it.isEnabled = false
                    pedidoOnClickRemoveListener?.invoke(pedido)
                }
            }

            Picasso
                .get()
                .load("${pedido.image}")
                .error(R.drawable.banner)
                .placeholder(R.drawable.banner)
                .into(imageView);

            nome.text = pedido.comida_title
            quantity.text = itemView.context.getString(R.string.quantity_pedido_x, pedido.quantity)
            obs.text = pedido.obs
            price.text = Utils.format(pedido.comida_preco!!)
        }
    }
}
