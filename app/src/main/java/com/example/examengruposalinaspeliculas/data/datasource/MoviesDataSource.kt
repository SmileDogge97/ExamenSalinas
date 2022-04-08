package com.example.examengruposalinaspeliculas.data.datasource

import com.example.examengruposalinaspeliculas.framework.data.model.ResponseList
import retrofit2.Response

interface MoviesDataSource {
    suspend fun getMovies(list: String, apiKey: String, page: String, sortBy: String): Response<ResponseList>
}