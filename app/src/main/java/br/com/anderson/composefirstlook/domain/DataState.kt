package br.com.anderson.composefirstlook.domain


sealed class DataState<T>() {
    class Success<T>(val data: T): DataState<T>()
    class Loading<T>: DataState<T>()
    class Failure<T>(val error: String?): DataState<T>()
}
