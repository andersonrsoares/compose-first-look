package br.com.anderson.composefirstlook.data.local.datasource


import br.com.anderson.composefirstlook.data.local.CityWeatherDao
import br.com.anderson.composefirstlook.data.local.entity.CityWeatherEntity


import javax.inject.Inject


class CityWeatherLocalDataSource @Inject constructor(
    private val dao: CityWeatherDao
    ) : ICityWeatherLocalDataSource {

    override suspend fun addCityWeather(weather: CityWeatherEntity) {
         kotlin.runCatching {
             dao.insertCityWeather(weather)
        }.onFailure {
            print(it)
         }
    }

    override suspend fun getLastFiveCitiesSearched(): List<CityWeatherEntity> {
       return kotlin.runCatching {
           dao.getLastFiveCities()
       }.getOrElse {
           print(it)
           arrayListOf()
       }
    }
}