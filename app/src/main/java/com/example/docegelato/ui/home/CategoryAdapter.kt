package com.example.docegelato.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.docegelato.R
import com.example.docegelato.model.categorias.CategoriasItem

class CategoryAdapter(
    private val onItemClickListener: ((Int) -> Unit)? = null) :
    RecyclerView.Adapter<CategoryAdapter.MainViewHolder>() {

    private var categoriasList = ArrayList<CategoriasItem>()

    fun setCategoriasList(comidasList: ArrayList<CategoriasItem>){
        this.categoriasList = comidasList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.categoria_item, parent, false)
//        val view = layoutInflater.inflate(R.layout.movie_item,parent,false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val currentItem = categoriasList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return categoriasList.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(currentItem: CategoriasItem) {

            val rvCategory: RecyclerView = itemView.findViewById(R.id.rv_comidas)
            val categoryTitle = itemView.findViewById<TextView>(R.id.tv_category_title)

            categoryTitle.text = currentItem.title
            val adapter = ComidasAdapter(currentItem.comidas, R.layout.comida_item, onItemClickListener)
            rvCategory.adapter = adapter
            rvCategory.layoutManager =
                LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
        }
    }
}