package br.com.anderson.composefirstlook.data.remote

sealed class RemoteDataSourceResult<T> {
    class Success<T>(val data: T): RemoteDataSourceResult<T>()
    class Error<T>(val error: RemoteDataSourceError): RemoteDataSourceResult<T>()
}

sealed class RemoteDataSourceError {
    object NetworkError: RemoteDataSourceError()
    object Unauthorized: RemoteDataSourceError()
    object NotFound: RemoteDataSourceError()
    class General(val error: String?): RemoteDataSourceError()
}