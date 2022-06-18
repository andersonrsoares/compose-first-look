package br.com.anderson.composefirstlook.presentation.weather_detail

import br.com.anderson.composefirstlook.domain.converter.TemperatureConverter
import br.com.anderson.composefirstlook.domain.model.Weather
import java.text.SimpleDateFormat
import java.util.*

fun Weather.formatDate(): String {
    return SimpleDateFormat("EEE'\n'dd").format(Calendar.getInstance().apply {
        timeInMillis = this@formatDate.date
    }.time)
}

fun Weather.formatTemperature(): String {
    return "${temperature.toInt()}º"
}