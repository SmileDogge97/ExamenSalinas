package com.example.examengruposalinaspeliculas.framework.data.datasource

import com.example.examengruposalinaspeliculas.framework.data.model.ResponseList
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MoviesEndPoint {

    @GET("/4/list/{list}")
    suspend fun getMovies(
        @Path("list") list: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: String,
        @Query("sort_by") sortBy: String
    ): Response<ResponseList>
}