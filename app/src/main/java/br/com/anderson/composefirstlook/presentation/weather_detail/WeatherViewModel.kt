package br.com.anderson.composefirstlook.presentation.weather_detail

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


    private val _fetchWeatherFlow = MutableStateFlow<UiState<List<Weather>>>(UiState.Loading())
    val fetchWeatherFlow = _fetchWeatherFlow.asSharedFlow()


    fun onWeatherSearchClick(cityName: String) {
        weatherRepository.fetchWeatherByCity(cityName)
            .onEach {
                when(it) {
                    is DataState.Success -> _fetchWeatherFlow.value  = UiState.Success(listOf(it.data))
                    is DataState.Loading -> _fetchWeatherFlow.value  = UiState.Loading()
                    is DataState.Failure -> _fetchWeatherFlow.value  = UiState.Failure(it.error.orEmpty())
                }
        }.launchIn(viewModelScope)
    }
}