package br.com.anderson.composefirstlook.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityWeatherEntity(
    @PrimaryKey
    var id: Long ,
    var description: String = "",
    var urlIcon: String = "",
    var temperature: Double = 0.0,
    var city: String = "",
    var date: Long = 0,
    var timestamp: Long = 0
)