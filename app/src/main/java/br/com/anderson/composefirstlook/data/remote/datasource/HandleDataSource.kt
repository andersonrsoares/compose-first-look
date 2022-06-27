package br.com.anderson.composefirstlook.data.remote.datasource


import kotlinx.coroutines.TimeoutCancellationException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.awaitResponse
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException


suspend fun <T> Call<T>.await(): RemoteDataSourceResult<T> {
    return runCatching {
        this.awaitResponse().handleResponse()
    }.getOrElse {
        RemoteDataSourceResult.Error(it.handleException())
    }
}

fun <T> Response<T>.handleResponse() : RemoteDataSourceResult<T> {
    return if (this.isSuccessful) {
        RemoteDataSourceResult.Success(this.body()!!)
    } else {
        RemoteDataSourceResult.Error(this.handleServerError())
    }
}

fun Throwable.handleException(): RemoteDataSourceError {
    return when(this){
        is UnknownHostException,
        is TimeoutException,
        is TimeoutCancellationException,
        is IOException -> RemoteDataSourceError.NetworkError
        else -> RemoteDataSourceError.UnknownError
    }
}

fun <T> Response<T>.handleServerError() : RemoteDataSourceError {
    val message = this.extractMessage()
    return when(this.code()){
        401 -> RemoteDataSourceError.Unauthorized(message)
        404 -> RemoteDataSourceError.NotFound(message)
        else -> RemoteDataSourceError.UnknownError
    }
}

fun <T> Response<T>.extractMessage() : String {
    return try {
        JSONObject(this.errorBody()?.string()!!).getString("message")
    } catch (e:Exception){
        this.errorBody()?.string() ?: ""
    }
}
