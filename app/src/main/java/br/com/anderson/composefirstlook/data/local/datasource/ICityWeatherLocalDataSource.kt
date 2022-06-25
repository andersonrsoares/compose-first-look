package br.com.anderson.composefirstlook.data.local.datasource

import br.com.anderson.composefirstlook.data.local.entity.CityWeatherEntity


interface ICityWeatherLocalDataSource {
    suspend fun getLastFiveCitiesSearched() : List<CityWeatherEntity>

    suspend fun addCityWeather(weather: CityWeatherEntity)
}