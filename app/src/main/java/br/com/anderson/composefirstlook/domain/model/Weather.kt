package br.com.anderson.composefirstlook.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    var description: String = "",
    var urlIcon: String = "",
    var temperature: Double = 0.0,
    var city: String = "",
    var date: Long = 0
) : Parcelable