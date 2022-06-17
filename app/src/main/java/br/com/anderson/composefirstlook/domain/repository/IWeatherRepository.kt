package br.com.anderson.composefirstlook.domain.repository

import br.com.anderson.composefirstlook.domain.DataState
import br.com.anderson.composefirstlook.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface IWeatherRepository {
    fun fetchWeatherByCity(cityName: String): Flow<DataState<Weather>>
}