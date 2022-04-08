package com.example.examengruposalinaspeliculas.framework.presentation.viewstate

import com.example.examengruposalinaspeliculas.framework.data.model.ResponseList
import com.example.examengruposalinaspeliculas.framework.data.model.Result

sealed class MoviesViewState {
    object Loading : MoviesViewState()
    data class Success(val list: List<Result>) : MoviesViewState()
    data class Error(val error: String): MoviesViewState()
    object MoviesNotFound: MoviesViewState()
}
