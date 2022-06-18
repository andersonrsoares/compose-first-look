package br.com.anderson.composefirstlook.domain.model

import br.com.anderson.composefirstlook.data.remote.dto.WeatherContentDto


fun WeatherContentDto.toWeather() : Weather =
    Weather(
        description = this.weather.first().description,
        urlIcon = this.weather.first().urlIcon,
        city = this.name,
        date = this.dt,
        temperature = this.main.temp
    )


