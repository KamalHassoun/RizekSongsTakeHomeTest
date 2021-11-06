package com.test.rizeksongstest.helpers

sealed class DataRequestResource<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {
    class Success<T>(data: T) : DataRequestResource<T>(data)
    class Loading<T> : DataRequestResource<T>()
    class Error<T>(throwable: Throwable?, data: T? = null) : DataRequestResource<T>(data, throwable)
}