package com.joel.jotspot.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _uiEvents = Channel<NavEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun onEvents(events: HomeEvents){
        when(events){
            HomeEvents.AddNote -> {
                viewModelScope.launch {
                    _uiEvents.send(NavEvents.NavToEditNote)
                }
            }
            HomeEvents.OnAvatarClick -> {
                viewModelScope.launch {
                    _uiEvents.send(NavEvents.NavToProfile)
                }
            }
            HomeEvents.OnSearchClick -> {
                viewModelScope.launch {
                    _uiEvents.send(NavEvents.SearchClick)
                }
            }
        }
    }
}

sealed class HomeEvents{
    data object AddNote : HomeEvents()
    data object OnAvatarClick : HomeEvents()
    data object OnSearchClick : HomeEvents()
    data object NoteClick : NavEvents()
}

sealed class NavEvents{
    data object NavToEditNote : NavEvents()
    data object NavToProfile : NavEvents()
    data object SearchClick : NavEvents()
}