package br.com.anderson.composefirstlook.domain.converter

object TemperatureConverter {
    fun kelvinToCelsius(temperature: Double) : Double {
       return (temperature  - 273.15F)
    }
}