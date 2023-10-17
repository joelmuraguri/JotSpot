package com.joel.jotspot.utils

sealed class RequestState<out T> {

    data object Idle : RequestState<Nothing>()
    data object Loading : RequestState<Nothing>()
    data class Success<T>(val data : T) : RequestState<T>()

}