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
import com.example.docegelato.util.Utils.format
import com.example.docegelato.util.Utils.formatDate
import java.util.*

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
            val precoTotal = itemView.findViewById<TextView>(R.id.tv_pedido_feito_total)
            val addressRua = itemView.findViewById<TextView>(R.id.tv_address_rua)
            val rvDentro = itemView.findViewById<RecyclerView>(R.id.rv_dentro_pedido_feito)
            val status = itemView.findViewById<TextView>(R.id.tv_status)
            val data = itemView.findViewById<TextView>(R.id.tv_pedido_feito_data)

            addressRua.text = "${pedidos.address?.bairro}"

            val adapter = PedidoAdapter(true)
            rvDentro.adapter = adapter
            adapter.addPedidoToRecyclerViewList(pedidos.pedidos!!)
            rvDentro.layoutManager = LinearLayoutManager(itemView.context)
            precoTotal.text = itemView.context.getString(R.string.total_price,format(pedidos.preco_total))
            status.text = pedidos.status
            data.text = formatDate(pedidos.date)

        }
    }
}