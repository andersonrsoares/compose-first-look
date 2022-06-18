package br.com.anderson.composefirstlook.data.remote.datasource

import br.com.anderson.composefirstlook.data.remote.dto.WeatherContentDto
import br.com.anderson.composefirstlook.data.remote.RemoteDataSourceResult


interface IWeatherRemoteDataSource {
    suspend fun findWeatherByCity(cityName: String) : RemoteDataSourceResult<WeatherContentDto>
}