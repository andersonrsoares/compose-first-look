package br.com.anderson.composefirstlook.domain.model

import br.com.anderson.composefirstlook.data.dto.WeatherContentDto


fun WeatherContentDto?.toWeather() : Weather {
    if (this == null)
        return Weather()

    return Weather(this.weather.first().description, icon = "")
}

