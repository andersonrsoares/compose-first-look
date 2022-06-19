package br.com.anderson.composefirstlook.presentation.weather_search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.anderson.composefirstlook.R
import br.com.anderson.composefirstlook.domain.DataState
import br.com.anderson.composefirstlook.domain.FailureReason
import br.com.anderson.composefirstlook.domain.model.CityWeather
import br.com.anderson.composefirstlook.domain.repository.ICityWeatherRepository
import br.com.anderson.composefirstlook.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class WeatherSearchViewModel @Inject constructor() : ViewModel() {


//    private val _fetchWeatherFlow = MutableStateFlow()
//    val fetchWeatherFlow = _fetchWeatherFlow.asSharedFlow()


}