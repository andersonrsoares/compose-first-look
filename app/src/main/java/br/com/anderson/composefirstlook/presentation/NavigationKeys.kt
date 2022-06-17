package br.com.anderson.composefirstlook.presentation

object NavigationKeys {
    object Arg {
        const val CITY_NAME = "CITY_NAME"
    }

    object Route {
        const val WEATHER_SEARCH = "WEATHER_SEARCH"
        const val WEATHER_DETAIL = "WEATHER_DETAIL/{${Arg.CITY_NAME}}"
        const val WEATHER_HISTORY = "WEATHER_HISTORY"
    }

    object Navigation {
        const val WEATHER_DETAIL = "WEATHER_DETAIL"
    }


}