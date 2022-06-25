package br.com.anderson.composefirstlook.presentation.navigation

sealed class NavigationScreen(val route: String) {
    object WeatherSearch : NavigationScreen("WEATHER_SEARCH")
    object WeatherDetail : NavigationScreen("WEATHER_DETAIL")
    object WeatherHistory : NavigationScreen("WEATHER_HISTORY")
}
object NavigationKeys {
    object Arg {
        const val CITY_NAME = "CITY_NAME"
    }
}

