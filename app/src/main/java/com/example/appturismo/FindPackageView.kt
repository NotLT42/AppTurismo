package com.example.appturismo

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FindPackageView : AppCompatActivity() {
    lateinit var service: PlaceHolderApi
    lateinit var plan: TourInfo
    private lateinit var appDB: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_find_package)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dev.formandocodigo.com/ServicioTour/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        appDB = AppDataBase.getDatabase(this)

        service = retrofit.create<PlaceHolderApi>(PlaceHolderApi::class.java)

        val txtSitio = findViewById<TextView>(R.id.txtSitio)
        val txtType = findViewById<TextView>(R.id.txtType)
        val btnConsultar = findViewById<TextView>(R.id.btnConsultar)
        val button_back = findViewById<TextView>(R.id.back)


        button_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnConsultar.setOnClickListener(){
            getListPlanes(txtSitio.text.toString(), txtType.text.toString().toInt())
        }

    }

    private fun getListPlanes(sitio: String, type: Int){
        service.getPlanes(sitio, type).enqueue(object : Callback<List<TourInfo>> {
            override fun onResponse(call: Call<List<TourInfo>>, response: Response<List<TourInfo>>) {
                if (response.isSuccessful) {
                    val tours = response.body()
                    tours?.let {
                        val container = findViewById<LinearLayout>(R.id.container_cards)
                        container.removeAllViews()
                        val marginBetweenCards = resources.getDimensionPixelSize(R.dimen.margin_between_cards)
                        // Itera sobre la lista de tours y configura las vistas
                        for (tour in tours) {
                            val cardView = layoutInflater.inflate(R.layout.card, null)
                            val params = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            val txtIdProduct = cardView.findViewById<TextView>(R.id.txtCardIdProducto)
                            val txtNombre = cardView.findViewById<TextView>(R.id.txtCardName)
                            val txtDescription = cardView.findViewById<TextView>(R.id.txtCardDescription)
                            val imgPhoto = cardView.findViewById<ImageView>(R.id.imgCardPhoto)
                            val txtUbication = cardView.findViewById<TextView>(R.id.txtCardUbication)
                            val btnFav = cardView.findViewById<TextView>(R.id.btnFav)

                            params.setMargins(0, 0, 0, marginBetweenCards)
                            cardView.layoutParams = params

                            txtIdProduct.text = "ID Producto: ${tour.idProducto}"
                            txtNombre.text = "Nombre: ${tour.nombre}"
                            txtDescription.text = "Descripcion:  ${tour.descripcion}"
                            txtUbication.text = "Ubicacion:  ${tour.ubicacin}"

                            Picasso.get().load(tour.imagen).into(imgPhoto)


                            var isFavorite = false

                            btnFav.setOnClickListener {
                                isFavorite = !isFavorite

                                val drawableRes = if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                                btnFav.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableRes, 0)

                                val tableFavoritos = TableFavoritos(
                                    id = null, // El ID se autogenera automáticamente
                                    Nombre = tour.nombre,
                                    Descripcion = tour.descripcion,
                                    Foto = tour.imagen
                                )
                                // Insertar en la base de datos
                                GlobalScope.launch {
                                    appDB.favoritesDAO().insertFavoritos(tableFavoritos)
                                }
                            }

                            // Agrega el cardView a tu layout contenedor
                            // container_cards es el ID del layout contenedor en tu XML
                            container.addView(cardView)
                        }
                    }
                } else {
                    // Manejar respuesta no exitosa
                }
            }

            override fun onFailure(call: Call<List<TourInfo>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }



}