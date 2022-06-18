package br.com.anderson.composefirstlook.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class WeatherEntity(
    var description: String = "",
    var urlIcon: String = "",
    var temperature: Double = 0.0,
    var city: String = "",
    var date: Long = 0
)