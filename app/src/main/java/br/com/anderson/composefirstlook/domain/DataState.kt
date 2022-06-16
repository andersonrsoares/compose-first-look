package br.com.anderson.composefirstlook.domain

import br.com.anderson.composefirstlook.data.remote.RemoteDataSourceError


sealed class DataState<T>(val data: T? = null, val error: String? = null) {
    class Success<T>(data: T): DataState<T>(data)
    class Loading<T>: DataState<T>()
    class Failure<T>(error: String?): DataState<T>(null, error)
}
