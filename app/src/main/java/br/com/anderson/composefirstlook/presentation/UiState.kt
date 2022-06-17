package br.com.anderson.composefirstlook.presentation


sealed class UiState<T> {
    class Success<T>(val data: T): UiState<T>()
    class Loading<T>: UiState<T>()
    class Failure<T>(val error: String?): UiState<T>()
}
