package com.example.appturismo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favoritos_table")
data class TableFavoritos (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "Nombre")
    val Nombre: String?,
    @ColumnInfo(name = "Descripcion")
    val Descripcion: String?,
    @ColumnInfo(name = "Foto")
    val Foto: String?
)