package com.example.examengruposalinaspeliculas.domain.usecase

import com.example.examengruposalinaspeliculas.domain.repository.MoviesRepository
import com.example.examengruposalinaspeliculas.framework.data.model.ResponseList
import com.example.examengruposalinaspeliculas.utils.UseCase
import retrofit2.Response
import javax.inject.Inject

class MoviesUseCase @Inject constructor(private val moviesRepository: MoviesRepository) :
    UseCase<MoviesUseCase.Params, Response<ResponseList>>() {

    override suspend fun execute(params: Params): Response<ResponseList> {
        params.let {
            return moviesRepository.attemptLoadMovies(
                params.list,
                params.apiKey,
                params.page,
                params.sortBy
            )
        }
    }

    data class Params(
        val list: String,
        val apiKey: String,
        val page: String,
        val sortBy: String
    )
}