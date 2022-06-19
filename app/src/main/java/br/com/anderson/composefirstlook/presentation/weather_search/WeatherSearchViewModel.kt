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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherSearchViewModel @Inject constructor() : ViewModel() {
//
//    private val _validateSearchInput = MutableSharedFlow<UiState<String>>()
//    val validateSearchInput = _validateSearchInput.asSharedFlow()


    private val _validateSearchInput = Channel<UiState<String>>(Channel.UNLIMITED)
    val validateSearchInput = _validateSearchInput

    fun onSearchClick(searchInputValue: String?) {
        viewModelScope.launch {
            if (searchInputValue.isNullOrBlank()) {
                _validateSearchInput.send(UiState.Failure(""))
            } else {
                _validateSearchInput.send(UiState.Success(searchInputValue))
            }
        }

    }
}