package com.example.examengruposalinaspeliculas.framework.data.datasource

import android.util.Log
import com.example.examengruposalinaspeliculas.data.datasource.MoviesDataSource
import com.example.examengruposalinaspeliculas.framework.data.model.ResponseList
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MoviesDataSourceImpl @Inject constructor(
    private val endPoint: MoviesEndPoint,
    private val coroutineContext: CoroutineContext
) : MoviesDataSource {

    override suspend fun getMovies(
        list: String,
        apiKey: String,
        page: String,
        sortBy: String
    ): Response<ResponseList> =
        withContext(coroutineContext) {
            val response = endPoint.getMovies(list, apiKey, page, sortBy)
            return@withContext response
        }
}