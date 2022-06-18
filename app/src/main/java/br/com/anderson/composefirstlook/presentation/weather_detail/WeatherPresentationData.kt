package br.com.anderson.composefirstlook.presentation.weather_detail

import br.com.anderson.composefirstlook.domain.model.CityWeather
import java.text.SimpleDateFormat
import java.util.*

fun CityWeather.formatDate(): String {
    return SimpleDateFormat("EEE'\n'dd").format(Calendar.getInstance().apply {
        timeInMillis = this@formatDate.date
    }.time)
}

fun CityWeather.formatTemperature(): String {
    return "${temperature.toInt()}º"
}