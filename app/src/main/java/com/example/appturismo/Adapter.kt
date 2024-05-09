package com.example.appturismo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Adapter(val listaFav:MutableList<TableFavoritos>, private val appDB:AppDataBase): RecyclerView.Adapter<FavoritosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritosViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FavoritosViewHolder(layoutInflater.inflate(R.layout.card_favorites,parent,false))
    }

    override fun onBindViewHolder(holder: FavoritosViewHolder, position: Int) {
        val item=listaFav[position]
        holder.render(item)

        holder.btnDesFav.setOnClickListener {
            // Eliminar el favorito de la base de datos
            try {
                GlobalScope.launch(Dispatchers.IO) {
                    appDB.favoritesDAO().deleteFavorito(item)
                }
                // Eliminar el elemento de la lista
                listaFav.removeAt(position)
                notifyItemRemoved(position)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun getItemCount(): Int = listaFav.size
}

