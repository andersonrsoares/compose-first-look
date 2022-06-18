package br.com.anderson.composefirstlook.presentation.weather_detail

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
class WeatherViewModel @Inject constructor(
    private val weatherRepository: ICityWeatherRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {


    private val _fetchWeatherFlow = MutableStateFlow<UiState<List<CityWeather>>>(UiState.Loading())
    val fetchWeatherFlow = _fetchWeatherFlow.asSharedFlow()


    fun onWeatherSearchClick(cityName: String) {
        weatherRepository.fetchWeatherByCity(cityName)
            .onEach {
                when(it) {
                    is DataState.Success -> _fetchWeatherFlow.value  = UiState.Success(listOf(it.data))
                    is DataState.Loading -> _fetchWeatherFlow.value  = UiState.Loading()
                    is DataState.Failure -> _fetchWeatherFlow.value  = getFailureMessage(it)
                }
        }.launchIn(viewModelScope)
    }

    fun getCityWeatherHistory() {
        weatherRepository.fetchWeatherCityHistory()
            .onEach {
                when(it) {
                    is DataState.Loading -> _fetchWeatherFlow.value  = UiState.Loading()
                    is DataState.Failure -> _fetchWeatherFlow.value  = UiState.Failure(context.getString(R.string.error_city_weather))
                    is DataState.Success -> _fetchWeatherFlow.value  =  when {
                        it.data.isNotEmpty() -> UiState.Success(it.data)
                        else -> UiState.Empty(context.getString(R.string.empty_city_weather_history))
                    }

                }
            }.launchIn(viewModelScope)
    }

    private fun getFailureMessage(dateState : DataState.Failure<CityWeather>) : UiState<List<CityWeather>> {
       return when(dateState.reason) {
            is FailureReason.ServerError -> UiState.Failure(dateState.reason.message)
            else ->  UiState.Failure(context.getString(R.string.error_city_weather))
        }
    }
}