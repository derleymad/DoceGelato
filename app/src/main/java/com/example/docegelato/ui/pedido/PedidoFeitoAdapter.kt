package com.example.docegelato.ui.pedido

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docegelato.R
import com.example.docegelato.model.categorias.Pedidos
import kotlin.collections.ArrayList

class PedidoFeitoAdapter(
    private var comidaOnClickListener: ((Int) -> Unit)? = null
): RecyclerView.Adapter<PedidoFeitoAdapter.PedidoFeitoViewHolder>() {

    private var pedidoFeitoList = ArrayList<Pedidos>()

    fun setPedidoFeitoList(pedidoFeitoList: ArrayList<Pedidos>){
        this.pedidoFeitoList=pedidoFeitoList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoFeitoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pedido_feito_item,parent,false)
        return PedidoFeitoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoFeitoViewHolder, position: Int) {
        val itemCurrent = pedidoFeitoList[position]
        holder.bind(itemCurrent)
    }

    override fun getItemCount(): Int {
        return pedidoFeitoList.size
    }

    inner class PedidoFeitoViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(pedidos: Pedidos){
            val nomePessoa = itemView.findViewById<TextView>(R.id.tv_pedido_feito_nome_pessoa)
            val precoTotal = itemView.findViewById<TextView>(R.id.tv_total_pedido)
            val addressRua = itemView.findViewById<TextView>(R.id.tv_address_rua)
//            val desc = itemView.findViewById<TextView>(R.id.descricao)
//            val imageView = itemView.findViewById<ImageView>(R.id.foto)
//            val format = NumberFormat.getCurrencyInstance(Locale("pt-br", "br"))
//            Picasso
//                .get()
//                .load("${comida.image}")
//                .error(R.drawable.banner)
//                .placeholder(R.drawable.banner)
//                .into(imageView);

            nomePessoa.text = "${pedidos.user?.nome}"
            addressRua.text = "${pedidos.address?.bairro}"
//            desc.text = comida.comida_desc
//            preco.text =  if(comida.comida_preco!=null) format.format(comida.comida_preco ) else "Preço por tamanho ou porção"


        }
    }
}