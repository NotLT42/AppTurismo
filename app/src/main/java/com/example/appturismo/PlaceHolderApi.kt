package com.example.appturismo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceHolderApi {
    @GET("productossitiotipo.php")
    fun getPlanes(@Query("sitio")sitio:String, @Query("tipo")tipo:Int): Call<List<TourInfo>>
}
