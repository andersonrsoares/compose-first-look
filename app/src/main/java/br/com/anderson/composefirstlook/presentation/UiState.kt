package br.com.anderson.composefirstlook.presentation


sealed class UiState<T>(val data: T?) {
    class Success<T>(data: T): UiState<T>(data)
    class Loading<T>: UiState<T>(null)
    class Failure<T>(val error: String?): UiState<T>(null)
}
