package br.com.anderson.composefirstlook.domain.converter

import javax.inject.Inject


class TemperatureConverter @Inject constructor() : ITemperatureConverter {

    override fun convert(temperature: Double) : Double {
       return (temperature  - 273.15F)
    }

}