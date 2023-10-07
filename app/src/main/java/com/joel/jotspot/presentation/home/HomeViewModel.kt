package com.joel.jotspot.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.domain.use_case.note.NoteUseCases
import com.joel.jotspot.navigation.Screens
import com.joel.jotspot.utils.JotSpotEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val notesUseCases : NoteUseCases
) : ViewModel() {

    val notes = notesUseCases.getNotesUseCase()

    private val _state = MutableStateFlow(HomeScreenState())
    val state : StateFlow<HomeScreenState> = _state
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
                    _uiEvents.send(JotSpotEvents.Navigate(Screens.Search.route ))
                }
            }
            is HomeEvents.OnNoteClick -> {
                viewModelScope.launch {
                    _uiEvents.send(JotSpotEvents.Navigate(Screens.EditNote.route + "?noteId=${events.note.id}"))
                }
            }
        }
    }
}

data class HomeScreenState(
    val notes : List<NoteEntity> = emptyList(),
    val loading : Boolean = true,
    val error : String = ""
)

sealed class HomeEvents{
    data object OnAddNoteClick : HomeEvents()
    data object OnAvatarClick : HomeEvents()
    data object OnSearchClick : HomeEvents()
    data class OnNoteClick(val note : NoteEntity) : HomeEvents()
}
