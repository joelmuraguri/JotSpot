package com.joel.jotspot.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.jotspot.navigation.Screens
import com.joel.jotspot.utils.JotSpotEvents
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _uiEvents = Channel<JotSpotEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun onEvents(events: HomeEvents){
        when(events){
            HomeEvents.OnAddNoteClick -> {
                viewModelScope.launch {
                    _uiEvents.send(JotSpotEvents.Navigate(Screens.EditNote.route))
                }
            }
            HomeEvents.OnAvatarClick -> {
                viewModelScope.launch {
                    _uiEvents.send(JotSpotEvents.Navigate(Screens.Profile.route))
                }
            }
            HomeEvents.OnSearchClick -> {
                viewModelScope.launch {
                    _uiEvents.send(JotSpotEvents.Navigate(Screens.Search.route))
                }
            }
            HomeEvents.OnNoteClick -> TODO()
        }
    }
}

sealed class HomeEvents{
    data object OnAddNoteClick : HomeEvents()
    data object OnAvatarClick : HomeEvents()
    data object OnSearchClick : HomeEvents()
    data object OnNoteClick : HomeEvents()
}
