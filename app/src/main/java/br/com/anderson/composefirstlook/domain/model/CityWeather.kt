package br.com.anderson.composefirstlook.domain.model

import android.os.Parcelable
import br.com.anderson.composefirstlook.domain.converter.TemperatureConverter
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityWeather(
    var description: String = "",
    var urlIcon: String = "",
    var temperature: Double = 0.0,
    var city: String = "",
    var date: Long = 0
) : Parcelable