package br.com.anderson.composefirstlook.data.remote

sealed class RemoteDataSourceResult<T>(val data: T? = null, val error:RemoteDataSourceError? = null) {
    class Success<T>(data: T): RemoteDataSourceResult<T>(data)
    class Error<T>(data: T? = null, error: RemoteDataSourceError?): RemoteDataSourceResult<T>(data, error)
}

sealed class RemoteDataSourceError(val error: String? = null) {
    object NetworkError: RemoteDataSourceError()
    object Unauthorized: RemoteDataSourceError()
    object NotFound: RemoteDataSourceError()
    class General(error: String?): RemoteDataSourceError(error)
}