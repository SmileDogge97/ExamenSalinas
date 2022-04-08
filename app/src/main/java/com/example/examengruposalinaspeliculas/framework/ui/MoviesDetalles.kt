package com.example.examengruposalinaspeliculas.framework.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.examengruposalinaspeliculas.R
import com.example.examengruposalinaspeliculas.databinding.ActivityMainBinding
import com.example.examengruposalinaspeliculas.databinding.ActivityMoviesDetallesBinding
import com.squareup.picasso.Picasso

class MoviesDetalles : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesDetallesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesDetallesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val extras = intent.extras
        val imagen = extras?.getString("imagen")?:""
        val titulo = extras?.getString("titulo")?:""
        val fecha= extras?.getString("fecha")?:""
        val idioma= extras?.getString("idioma")?:""
        val descripcion= extras?.getString("descripcion")?:""
        val popularidad= extras?.getString("popularidad")?:""

        Picasso.get().load("https://image.tmdb.org/t/p/w500"+imagen).into(binding.imageView)

        binding.txtTitulo.setText(titulo)
        binding.txtFechaRelease.setText(fecha)
        binding.txtIdioma.setText("("+idioma+")")
        if (descripcion.isNotEmpty())binding.txtDescripcion.setText(descripcion) else binding.txtDescripcion.setText("No hay descripcion")
        binding.txtPopularidad.setText("Popularity: "+popularidad)
        setSupportActionBar(binding.toolbar2)
        setupToolbar(titulo)
    }

    fun setupToolbar(title: String){
        supportActionBar?.title = title

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar2.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })
    }
}