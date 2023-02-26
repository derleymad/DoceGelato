package com.derleymad.docegelato.ui.perfil.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.derleymad.docegelato.R
import com.derleymad.docegelato.model.pedidos.Pedidos
import com.derleymad.docegelato.ui.home.adapters.PedidoAdapter
import com.derleymad.docegelato.util.Utils.format
import com.derleymad.docegelato.util.Utils.formatDate
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class AdapterPedidosAdmin (
    private var pedidoFeitoOnClickListener: ((Long?) -> Unit)? = null,
    private var pedidoRemovidoOnClickListener: ((Long?) -> Unit)? = null,
    private var valueBase : Int
        ): RecyclerView.Adapter<AdapterPedidosAdmin.PedidosAdminViewholder>(){
    val list = ArrayList<Pedidos>()

    @SuppressLint("NotifyDataSetChanged")
    fun setListToRecyclerView(list : ArrayList<Pedidos>){
        this.list.clear()
        this.list.addAll(
            list
        )
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidosAdminViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pedido_feito_item, parent, false)
        return PedidosAdminViewholder(view)
    }

    override fun onBindViewHolder(holder: PedidosAdminViewholder, position: Int) {
        val currentItem = list[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class PedidosAdminViewholder(view:View) : RecyclerView.ViewHolder(view){
        @SuppressLint("NotifyDataSetChanged")
        fun bind(currentItem : Pedidos){
            val text = itemView.findViewById<TextView>(R.id.tv_pedido_feito_nome_pessoa)
            val pedidoFeitoTotal = itemView.findViewById<TextView>(R.id.tv_pedido_feito_total)
            val date = itemView.findViewById<TextView>(R.id.tv_pedido_feito_data)
            val rua = itemView.findViewById<TextView>(R.id.tv_address_rua)
            val rv = itemView.findViewById<RecyclerView>(R.id.rv_dentro_pedido_feito)
            val image = itemView.findViewById<ImageView>(R.id.img_perfil_photo)
            val status = itemView.findViewById<TextView>(R.id.tv_status)
            val btnAceitar = itemView.findViewById<Button>(R.id.aceitar_pedido)
            val btnRecusar= itemView.findViewById<Button>(R.id.recusar_pedido)

//            Log.i("testando",currentItem.pedidos.toString())
//            date.text = SimpleDateFormat("dd/MM/yyyy").format(currentItem.date)
            Picasso
                .get()
                .load("${currentItem.user?.imagemPerfil}")
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(image);

            when(valueBase){
                0->{
                    btnAceitar.setOnClickListener {
                        pedidoFeitoOnClickListener?.invoke(currentItem.id)
                    }
                }
                1->{
                    btnAceitar.visibility = View.GONE
                    btnRecusar.apply {
                        this.text = "Finalizar"
                        setOnClickListener {
                            pedidoRemovidoOnClickListener?.invoke(currentItem.id)
                        }
                    }
                }
                2->{
                    btnAceitar.visibility = View.GONE
                    btnRecusar.visibility = View.GONE
                }
                else->{}
            }
            if(valueBase.equals(1)){

            }

            date.text = formatDate(currentItem.date)
            text.text = currentItem.user?.nome
            rua.text = itemView.context.getString(R.string.endereco_final,
                currentItem.address?.rua,
                currentItem.address?.bairro,
                currentItem.address?.numero_da_casa,
                currentItem.address?.ponto_referencia
            )
            pedidoFeitoTotal.text = format(currentItem.preco_total)
            status.text = currentItem.status



            val adapter = PedidoAdapter(true)
            rv.adapter = adapter
            rv.layoutManager = LinearLayoutManager(itemView.context)

            adapter.addPedidoToRecyclerViewList(currentItem.pedidos!!)
            Log.i("currentItem",currentItem.toString())

        }
    }
}