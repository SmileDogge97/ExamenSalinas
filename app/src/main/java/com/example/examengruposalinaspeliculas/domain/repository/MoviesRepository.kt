package com.example.examengruposalinaspeliculas.domain.repository

import com.example.examengruposalinaspeliculas.framework.data.model.ResponseList
import retrofit2.Response

interface MoviesRepository {
    suspend fun attemptLoadMovies(list: String, apiKey: String, page: String, sortBy: String) : Response<ResponseList>
}