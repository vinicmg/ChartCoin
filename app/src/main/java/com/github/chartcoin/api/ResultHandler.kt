package com.github.chartcoin.api

sealed class ResultHandler<out T> {
    data class Success<out T>(val result: T) : ResultHandler<T>()

    data class Error(val message: String? = null) : ResultHandler<Nothing>()
}
