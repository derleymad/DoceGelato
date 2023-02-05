package com.example.docegelato.ui.pedido

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.docegelato.R
import com.example.docegelato.model.pedidos.Pedidos
import com.example.docegelato.ui.home.adapters.PedidoAdapter

class PedidoFeitoAdapter(
    private var comidaOnClickListener: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<PedidoFeitoAdapter.PedidoFeitoViewHolder>() {

    private var pedidoFeitoList = ArrayList<Pedidos>()

    fun setPedidoFeitoList(pedidoFeitoList: ArrayList<Pedidos>) {
        this.pedidoFeitoList = pedidoFeitoList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoFeitoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pedido_feito_item_cliente, parent, false)
        return PedidoFeitoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoFeitoViewHolder, position: Int) {
        val itemCurrent = pedidoFeitoList[position]
        holder.bind(itemCurrent)
    }

    override fun getItemCount(): Int {
        return pedidoFeitoList.size
    }

    inner class PedidoFeitoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pedidos: Pedidos) {
//            val nomePessoa = itemView.findViewById<TextView>(R.id.tv_pedido_feito_nome_pessoa)
            val precoTotal = itemView.findViewById<TextView>(R.id.tv_total_pedido)
            val addressRua = itemView.findViewById<TextView>(R.id.tv_address_rua)
            val rvDentro = itemView.findViewById<RecyclerView>(R.id.rv_dentro_pedido_feito)


//            Picasso
//                .get()
//                .load("${comida.image}")
//                .error(R.drawable.banner)
//                .placeholder(R.drawable.banner)
//                .into(imageView);

//            nomePessoa.text = "${pedidos.user?.nome}"
            addressRua.text = "${pedidos.address?.bairro}"

            val adapter = PedidoAdapter(true)
            rvDentro.adapter = adapter
            adapter.addPedidoToRecyclerViewList(pedidos.pedidos!!)
            rvDentro.layoutManager = LinearLayoutManager(itemView.context)

        }
    }
}