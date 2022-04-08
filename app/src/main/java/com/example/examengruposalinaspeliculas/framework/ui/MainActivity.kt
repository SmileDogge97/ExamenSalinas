package com.example.examengruposalinaspeliculas.framework.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examengruposalinaspeliculas.R
import com.example.examengruposalinaspeliculas.databinding.ActivityMainBinding
import com.example.examengruposalinaspeliculas.framework.data.model.ResponseList
import com.example.examengruposalinaspeliculas.framework.data.model.Result
import com.example.examengruposalinaspeliculas.framework.presentation.MoviesViewModel
import com.example.examengruposalinaspeliculas.framework.presentation.viewstate.MoviesViewState
import com.example.examengruposalinaspeliculas.framework.ui.adapter.MoviesListAdapter
import com.example.examengruposalinaspeliculas.utils.ValidarR
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val moviesViewModel: MoviesViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var moviesAdapter: MoviesListAdapter
    private lateinit var context: Context
    private var filterActual = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        context = this
        attachObservers()
        bindViews()
    }

    fun attachObservers() {
        moviesViewModel.moviesState.observe(
            this, {
                renderMovies(it)
            }
        )
    }

    fun bindViews(): Unit = with(binding) {
        if (ValidarR.hayRed(this@MainActivity)) {
            loadSpinner()
        } else {
            showNotFoundMessage("No hay internet banda x.x")
        }


        moviesAdapter = MoviesListAdapter(onMoviesSelected = { result ->
            val pasar:Intent = Intent(context, MoviesDetalles::class.java)
            pasar.putExtra("imagen", result.poster_path)
            pasar.putExtra("titulo", result.title)
            pasar.putExtra("fecha", result.release_date)
            pasar.putExtra("idioma", result.original_language)
            pasar.putExtra("descripcion", result.overview)
            pasar.putExtra("popularidad", result.popularity.toString())
            startActivity(pasar)
        })

        binding.list.apply {
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                @Override
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!binding.list.canScrollVertically(1)) {

                        when (moviesViewModel.page) {
                            2 -> {
                                moviesViewModel.loadMovies(
                                    filterActual,
                                    moviesViewModel.page.toString()
                                )
                                moviesViewModel.page = 3
                            }
                            3 -> {
                                moviesViewModel.loadMovies(
                                    filterActual,
                                    moviesViewModel.page.toString()
                                )
                                moviesViewModel.page = 4
                            }
                            4 -> {
                                moviesViewModel.loadMovies(
                                    filterActual,
                                    moviesViewModel.page.toString()
                                )
                                moviesViewModel.page = 5
                            }
                            5 -> {

                            }
                            else -> moviesViewModel.page = 4
                        }
                    }
                }
            })
        }
    }

    fun showLoader() {
        with(binding) {
            list.visibility = View.GONE
            pCargar.visibility = View.VISIBLE
        }
    }

    private fun renderMovies(it: MoviesViewState?) {
        when (it) {
            is MoviesViewState.Loading -> showLoader()
            is MoviesViewState.Success -> {
                hideLoader()
                displayMovies(it.list)
            }
            is MoviesViewState.Error -> {
                hideLoader()
                showNotFoundMessage("Error al cargar datos x.x")
            }
        }
    }

    private fun displayMovies(it: List<Result>) {
        val allMovies = it
        Log.d("MainActivity/displayMovies/allMovies", allMovies.toString())
        moviesAdapter.updateDataSet(allMovies.toMutableList())
    }

    fun hideLoader() {
        with(binding) {
            list.visibility = View.VISIBLE
            pCargar.visibility = View.GONE
        }
    }

    private fun showNotFoundMessage(messageError: String) {
        with(binding) {
            list.visibility = View.GONE
            pCargar.visibility = View.GONE
            txtMessage.visibility = View.VISIBLE
            txtMessage.setText(messageError)
        }
    }

    fun loadSpinner() {
        ArrayAdapter.createFromResource(
            context,
            R.array.filtros_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spFiltro.adapter = adapter
        }
        binding.spFiltro.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("SpinnerActivity/onItemSelected", p0?.getItemAtPosition(p2).toString())
                moviesViewModel.page = 1
                filterActual = p0?.getItemAtPosition(p2).toString()
                moviesAdapter.resetData()
                moviesViewModel.loadMovies(
                    p0?.getItemAtPosition(p2).toString(),
                    moviesViewModel.page.toString()
                )
                moviesViewModel.page++
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
}
