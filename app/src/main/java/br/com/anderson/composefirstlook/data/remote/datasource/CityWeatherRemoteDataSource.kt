package br.com.anderson.composefirstlook.data.remote.datasource

import br.com.anderson.composefirstlook.data.remote.dto.WeatherContentDto
import br.com.anderson.composefirstlook.data.remote.RemoteDataSourceResult
import br.com.anderson.composefirstlook.data.remote.network.WeatherService
import br.com.anderson.composefirstlook.data.remote.transformToRemoteDataSourceResult

import javax.inject.Inject


class CityWeatherRemoteDataSource @Inject constructor(
    private val service: WeatherService
    ) : ICityWeatherRemoteDataSource {

    override suspend fun findWeatherByCity(cityName: String) : RemoteDataSourceResult<WeatherContentDto> {
        return service.findWeather(cityName).transformToRemoteDataSourceResult()
    }
}