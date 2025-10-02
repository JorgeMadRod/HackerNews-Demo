package com.jmadrigal.hackernews.utils

sealed class GenericDataState<T>(val data: T? = null, val error: String? = null) {
    class Success<T>(data: T) : GenericDataState<T>(data)
    class Loading<T>(data: T? = null) : GenericDataState<T>(data)
    class Error<T>(error: String?, data: T? = null) : GenericDataState<T>(data, error)
    class IDLE<T> : GenericDataState<T>()
}
