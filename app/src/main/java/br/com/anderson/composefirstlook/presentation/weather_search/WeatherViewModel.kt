package br.com.anderson.composefirstlook.presentation.weather_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.anderson.composefirstlook.domain.repository.IWeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: IWeatherRepository
) : ViewModel() {

    fun onWeatherSearchClick(cityName: String) {
        viewModelScope.launch {
            weatherRepository.fetchWeatherByCity("dublin")
                .collectLatest {
                print(it)
            }
        }
    }
}