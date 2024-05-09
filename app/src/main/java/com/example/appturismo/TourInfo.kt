package com.example.appturismo

import com.google.gson.annotations.SerializedName

data class TourInfo(
    @SerializedName("0") val _0: String,
    @SerializedName("1") val _1: String,
    @SerializedName("2") val _2: String,
    @SerializedName("3") val _3: String,
    @SerializedName("4") val _4: String,
    @SerializedName("5") val _5: String,
    @SerializedName("6") val _6: String,
    @SerializedName("7") val _7: String,
    @SerializedName("8") val _8: String,
    @SerializedName("9") val _9: String,
    @SerializedName("10") val _10: String,
    @SerializedName("11") val _11: String,
    @SerializedName("12") val _12: String,
    @SerializedName("13") val _13: String,
    val idProducto: String,
    val nombre: String,
    val descripcion: String,
    val ubicacin: String,
    val idSitio: String,
    val estrellas: String,
    val imagen: String,
    val idTipo: String,
    val des1: String,
    val pre1: String,
    val des2: String,
    val pre2: String,
    val des3: String,
    val pre3: String
)