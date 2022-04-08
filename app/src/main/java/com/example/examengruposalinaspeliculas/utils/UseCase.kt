package com.example.examengruposalinaspeliculas.utils

abstract class UseCase<Params, ReturnValue> {
    abstract suspend fun execute(params: Params) : ReturnValue
}