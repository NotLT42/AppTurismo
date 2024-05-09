package com.example.appturismo

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoritePackageView: AppCompatActivity() {

    lateinit var Lista: MutableList<TableFavoritos>
    lateinit var adapter: Adapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favorite_packages)

        val appDB2:AppDataBase by lazy { AppDataBase.getDatabase(this) }
        val recyclerView: RecyclerView =findViewById(R.id.recyclerFavoritos)
        recyclerView.layoutManager= LinearLayoutManager(applicationContext)

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
        try {
            runOnUiThread{
                GlobalScope.launch (Dispatchers.IO){
                    Lista=appDB2.favoritesDAO().getAllFavoritos()
                    adapter=Adapter(Lista, appDB2)
                    adapter.notifyDataSetChanged()
                    recyclerView.adapter=adapter
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}