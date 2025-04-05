package com.test.posts.data

sealed class Async<out T> {
    data object Loading : Async<Nothing>()
    data class Error(val e: Throwable?) : Async<Nothing>()
    data class Success<out T>(val data: T): Async<T>()
}
