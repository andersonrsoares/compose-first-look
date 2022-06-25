package br.com.anderson.composefirstlook.data.local

import androidx.room.*
import br.com.anderson.composefirstlook.data.local.entity.CityWeatherEntity


@Dao
interface CityWeatherDao {

    @Query("SELECT * FROM CityWeatherEntity order by timestamp DESC limit 5")
    suspend fun getLastFiveCities(): List<CityWeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCityWeather(weather: CityWeatherEntity)

}