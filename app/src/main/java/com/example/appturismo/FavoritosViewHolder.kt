package com.example.appturismo

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoritosViewHolder(view: View): RecyclerView.ViewHolder(view){
    val cName = view.findViewById<TextView>(R.id.txtCardFavName)
    val cDescription = view.findViewById<TextView>(R.id.txtCardFavDescription)
    val cPhoto = view.findViewById<ImageView>(R.id.imgCardFavPhoto)
    val btnDesFav: TextView = itemView.findViewById(R.id.btnDesFav)

    fun render(objFavorito: TableFavoritos) {
        cName.text = objFavorito.Nombre.toString()
        cDescription.text = objFavorito.Descripcion.toString()

        Picasso.get().load(objFavorito.Foto).into(cPhoto)

        btnDesFav.setOnClickListener {
            // Eliminar el favorito de la base de datos
            val context = itemView.context
            val appDB = AppDataBase.getDatabase(context)
            GlobalScope.launch(Dispatchers.IO) {
                appDB.favoritesDAO().deleteFavorito(objFavorito)
            }

            val adapterPosition = adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                (itemView.parent as RecyclerView).adapter?.notifyItemRemoved(adapterPosition)
            }

        }
    }
}