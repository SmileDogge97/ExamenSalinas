package com.example.examengruposalinaspeliculas.framework.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examengruposalinaspeliculas.R
import com.example.examengruposalinaspeliculas.databinding.ItemMovieListBinding
import com.example.examengruposalinaspeliculas.framework.data.model.Result
import com.squareup.picasso.Picasso

class MoviesListAdapter(
    private val movies: MutableList<Result> = mutableListOf(),
    private val onMoviesSelected: (Result) -> Unit
) : RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_list, parent, false)
        return MoviesViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) =
        holder.bindView(movies[position], onMoviesSelected)

    fun updateDataSet(movies: MutableList<Result>) {
        //this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    fun resetData(){
        this.movies.clear()
        notifyDataSetChanged()
    }

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemMovieListBinding.bind(itemView)

        fun bindView(movies: Result, onMoviesSelected: (Result) -> Unit) {
            with(movies) {
                if (poster_path.isNotEmpty()) {
                    Picasso.get().load("https://image.tmdb.org/t/p/w500"+poster_path).centerInside().fit().into(binding.imgMovie)
                    binding.root.setOnClickListener {
                        onMoviesSelected(this)
                    }
                }
                if (!popularity.equals(null)){
                    binding.txtPopularidad.setText("Popularidad: "+popularity.toString())
                }
                if (release_date.isNotEmpty()){
                    binding.txtFechaLanzamiento.setText("Fecha de lanzamiento: "+release_date)
                }
            }
        }
    }
}