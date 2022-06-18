package br.com.anderson.composefirstlook.domain.model

import br.com.anderson.composefirstlook.data.local.entity.CityWeatherEntity
import br.com.anderson.composefirstlook.data.remote.dto.WeatherContentDto


fun WeatherContentDto.toCityWeather() : CityWeather =
    CityWeather(
        description = this.weather.first().description,
        urlIcon = this.weather.first().urlIcon,
        city = this.name,
        date = this.dt,
        temperature = this.main.temp
    )

fun WeatherContentDto.toCityWeatherEntity() : CityWeatherEntity =
    CityWeatherEntity(
        id = this.id.toLong(),
        description = this.weather.first().description,
        urlIcon = this.weather.first().urlIcon,
        city = this.name,
        date = this.dt,
        temperature = this.main.temp
    )

fun CityWeatherEntity.toCityWeather() : CityWeather =
    CityWeather(
        description = this.description,
        urlIcon = this.urlIcon,
        city = this.city,
        date = this.date,
        temperature = this.temperature
    )


