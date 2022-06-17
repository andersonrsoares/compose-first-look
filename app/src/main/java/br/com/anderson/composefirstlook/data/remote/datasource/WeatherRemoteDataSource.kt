package br.com.anderson.composefirstlook.data.remote.datasource

import br.com.anderson.composefirstlook.data.dto.WeatherContentDto
import br.com.anderson.composefirstlook.data.remote.RemoteDataSourceError
import br.com.anderson.composefirstlook.data.remote.RemoteDataSourceResult
import br.com.anderson.composefirstlook.data.remote.network.WeatherService

import javax.inject.Inject


class WeatherRemoteDataSource @Inject constructor(
    private val service: WeatherService
    ) : IWeatherRemoteDataSource {

    override suspend fun findWeatherByCity(cityName: String) : RemoteDataSourceResult<WeatherContentDto> {
          return try {
             val response = service.findWeather(cityName)
             if (response.isSuccessful) {
                 RemoteDataSourceResult.Success(response.body()!!)
             } else {
               RemoteDataSourceResult.Error(RemoteDataSourceError.General("error"))
            }
        } catch (e:Throwable) {
              RemoteDataSourceResult.Error(RemoteDataSourceError.General(e.message))
        }
    }
}