package com.example.examengruposalinaspeliculas.framework.di

import com.example.examengruposalinaspeliculas.data.datasource.MoviesDataSource
import com.example.examengruposalinaspeliculas.data.repository.MoviesRepositoryImpl
import com.example.examengruposalinaspeliculas.domain.repository.MoviesRepository
import com.example.examengruposalinaspeliculas.domain.usecase.MoviesUseCase
import com.example.examengruposalinaspeliculas.framework.data.datasource.MoviesDataSourceImpl
import com.example.examengruposalinaspeliculas.framework.data.datasource.MoviesEndPoint
import com.example.examengruposalinaspeliculas.framework.data.model.ResponseList
import com.example.examengruposalinaspeliculas.utils.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@InstallIn(SingletonComponent::class)
@Module
object MoviesModule {
    const val BASE_URL = "https://api.themoviedb.org"

    @Provides
    fun providesRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesMoviesEndPoint(
        retrofit: Retrofit
    ): MoviesEndPoint =
        retrofit.create(MoviesEndPoint::class.java)

    @Provides
    fun providesMoviesDataSource(
        endPoint: MoviesEndPoint
    ): MoviesDataSource =
        MoviesDataSourceImpl(endPoint, IO)

    @Provides
    fun providesMoviesRepository(
        datasource: MoviesDataSource
    ): MoviesRepository =
        MoviesRepositoryImpl(datasource)

    @Provides
    fun providesMoviesUseCaseProvider(
        moviesRepository: MoviesRepository
    ): UseCase<MoviesUseCase.Params, Response<ResponseList>> =
        MoviesUseCase(moviesRepository)
}