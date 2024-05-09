package com.example.appturismo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritosDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoritos(vararg  tableFavoritos: TableFavoritos)

    @Query("SELECT * FROM favoritos_table")
    fun getAllFavoritos(): MutableList<TableFavoritos>

    @Delete
    suspend fun deleteFavorito(favorito: TableFavoritos)
}