package br.com.anderson.composefirstlook.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.anderson.composefirstlook.data.local.entity.CityWeatherEntity

@Database(entities = [CityWeatherEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityWeatherDao(): CityWeatherDao
}