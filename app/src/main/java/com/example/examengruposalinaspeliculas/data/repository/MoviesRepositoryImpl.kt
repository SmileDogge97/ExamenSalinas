package com.example.examengruposalinaspeliculas.data.repository

import com.example.examengruposalinaspeliculas.data.datasource.MoviesDataSource
import com.example.examengruposalinaspeliculas.domain.repository.MoviesRepository
import com.example.examengruposalinaspeliculas.framework.data.datasource.MoviesDataSourceImpl
import com.example.examengruposalinaspeliculas.framework.data.model.ResponseList
import retrofit2.Response
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val datasource: MoviesDataSource) :
    MoviesRepository {
    override suspend fun attemptLoadMovies(
        list: String, apiKey: String, page: String, sortBy: String
    ): Response<ResponseList> = datasource.getMovies(list, apiKey, page, sortBy)

}