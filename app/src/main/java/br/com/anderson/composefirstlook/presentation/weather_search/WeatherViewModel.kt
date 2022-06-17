package br.com.anderson.composefirstlook.presentation.weather_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.anderson.composefirstlook.domain.DataState
import br.com.anderson.composefirstlook.domain.model.Weather
import br.com.anderson.composefirstlook.domain.repository.IWeatherRepository
import br.com.anderson.composefirstlook.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: IWeatherRepository
) : ViewModel() {


    private val _fetchWeatherFlow = MutableStateFlow<UiState<Weather>>(UiState.Loading())
    val fetchWeatherFlow = _fetchWeatherFlow.asSharedFlow()


    fun onWeatherSearchClick(cityName: String) {
        weatherRepository.fetchWeatherByCity("dublin")
            .onEach {
                when(it) {
                    is DataState.Success -> _fetchWeatherFlow.value  = UiState.Success(it.data)
                    is DataState.Loading -> _fetchWeatherFlow.value  = UiState.Loading()
                    is DataState.Failure -> _fetchWeatherFlow.value  = UiState.Failure(it.error)
                }
        }.launchIn(viewModelScope)
    }
}