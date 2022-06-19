package br.com.anderson.composefirstlook.domain


sealed class DataState<T>() {
    class Success<T>(val data: T): DataState<T>()
    class Loading<T>: DataState<T>()
    class Failure<T>(val reason: FailureReason): DataState<T>()
}


sealed class FailureReason() {
    class NetworkIssue : FailureReason()
    class ServerError(val message: String): FailureReason()
    class GenericError: FailureReason()
}
