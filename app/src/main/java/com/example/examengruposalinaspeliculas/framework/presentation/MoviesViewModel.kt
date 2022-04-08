package com.example.examengruposalinaspeliculas.framework.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.examengruposalinaspeliculas.domain.usecase.MoviesUseCase
import com.example.examengruposalinaspeliculas.framework.data.model.ResponseList
import com.example.examengruposalinaspeliculas.framework.data.model.Result
import com.example.examengruposalinaspeliculas.framework.presentation.viewstate.MoviesViewState
import com.example.examengruposalinaspeliculas.utils.ApiKeys
import com.example.examengruposalinaspeliculas.utils.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: UseCase<MoviesUseCase.Params, Response<ResponseList>>
) : ViewModel() {
    var page = 1
    private var _moviesState = MutableLiveData<MoviesViewState>()
    val moviesState: LiveData<MoviesViewState> get() = _moviesState

    fun loadMovies(sortBy: String, page:String) {
        Log.d("MoviesViewModel/loadMovies/page", page)
        _moviesState.postValue(MoviesViewState.Loading)
        viewModelScope.launch {
            val moviesResult = runCatching {
                when (sortBy) {
                    "Playing Now" -> {
                        moviesUseCase.execute(
                            MoviesUseCase.Params(
                                ApiKeys.list, ApiKeys.apiKey, page, "release_date.desc"
                            )
                        )
                    }
                    "Most Popular" -> {
                        moviesUseCase.execute(
                            MoviesUseCase.Params(
                                ApiKeys.list, ApiKeys.apiKey, page, "popularity.asc"
                            )
                        )
                    }
                    else -> {
                        moviesUseCase.execute(
                            MoviesUseCase.Params(
                                ApiKeys.list, ApiKeys.apiKey, page, "release_date.desc"
                            )
                        )
                    }
                }

            }
            moviesResult.onSuccess { movies ->
                val totalMovies = movies.body()?.results.orEmpty()
                if (totalMovies.isNotEmpty()) {
                    _moviesState.postValue(MoviesViewState.Success(movies.body()!!.results))
                } else {
                    _moviesState.postValue(MoviesViewState.MoviesNotFound)
                }
            }
            moviesResult.onFailure {
                _moviesState.postValue(MoviesViewState.Error(it.localizedMessage.orEmpty()))
            }
        }
    }
}