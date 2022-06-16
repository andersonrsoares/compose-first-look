package br.com.anderson.composefirstlook.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Weather(
    var description: String = "",
    var icon: String = "",
    var id: Int = 0,
    var main: String = ""
) : Parcelable