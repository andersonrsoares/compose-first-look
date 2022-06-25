package br.com.anderson.composefirstlook.domain.repository

import br.com.anderson.composefirstlook.domain.DataState
import br.com.anderson.composefirstlook.domain.model.CityWeather
import kotlinx.coroutines.flow.Flow

interface ICityWeatherRepository {
    fun fetchWeatherByCity(cityName: String): Flow<DataState<CityWeather>>

    fun fetchWeatherCityHistory(): Flow<DataState<List<CityWeather>>>
}