package com.joel.jotspot.utils

sealed class JotSpotEvents {

    data class Navigate(val route : String) : JotSpotEvents()
    data object PopBackStack : JotSpotEvents()
}