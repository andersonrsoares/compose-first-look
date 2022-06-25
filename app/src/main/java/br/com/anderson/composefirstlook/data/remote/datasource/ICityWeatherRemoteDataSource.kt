package br.com.anderson.composefirstlook.data.remote.datasource

import br.com.anderson.composefirstlook.data.remote.dto.WeatherContentDto


interface ICityWeatherRemoteDataSource {
    suspend fun findWeatherByCity(cityName: String) : RemoteDataSourceResult<WeatherContentDto>
}