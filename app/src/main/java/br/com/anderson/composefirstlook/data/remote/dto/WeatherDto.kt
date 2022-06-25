package br.com.anderson.composefirstlook.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherContentDto(
    @Json(name="base")
    var base: String = "",
    @Json(name="cod")
    var cod: Int = 0,
    @Json(name="dt")
    var dt: Long = 0,
    @Json(name="id")
    var id: Int = 0,
    @Json(name="main")
    var main: WeatherMainDto = WeatherMainDto(),
    @Json(name="name")
    var name: String = "",
    @Json(name="sys")
    var sys: WeatherSysDto = WeatherSysDto(),
    @Json(name="timezone")
    var timezone: Int = 0,
    @Json(name="visibility")
    var visibility: Int = 0,
    @Json(name="weather")
    var weather: List<WeatherDto> = listOf(),
)


@JsonClass(generateAdapter = true)
data class WeatherMainDto(
    @Json(name="feels_like")
    var feelsLike: Double = 0.0,
    @Json(name="humidity")
    var humidity: Int = 0,
    @Json(name="pressure")
    var pressure: Int = 0,
    @Json(name="temp")
    var temp: Double = 0.0,
    @Json(name="temp_max")
    var tempMax: Double = 0.0,
    @Json(name="temp_min")
    var tempMin: Double = 0.0
)

@JsonClass(generateAdapter = true)
data class WeatherSysDto(
    @Json(name="country")
    var country: String = "",
    @Json(name="id")
    var id: Int = 0,
    @Json(name="sunrise")
    var sunrise: Int = 0,
    @Json(name="sunset")
    var sunset: Int = 0,
    @Json(name="type")
    var type: Int = 0
)

@JsonClass(generateAdapter = true)
data class WeatherDto(
    @Json(name="description")
    var description: String = "",
    @Json(name="icon")
    var icon: String = "",
    @Json(name="id")
    var id: Int = 0,
    @Json(name="main")
    var main: String = "",
    val urlIcon: String = "http://openweathermap.org/img/wn/${icon}@2x.png"
)