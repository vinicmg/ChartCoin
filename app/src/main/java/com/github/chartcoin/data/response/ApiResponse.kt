package com.github.chartcoin.data.response

sealed class ApiResponse<out T> {
    data class Success<out T>(val result: T) : ApiResponse<T>()

    data class Error(val message: String? = null) : ApiResponse<Nothing>()
}
