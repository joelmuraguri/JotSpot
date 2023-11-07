package com.joel.jotspot.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.data.relations.NoteBookWithNotes
import com.joel.jotspot.domain.use_case.note_book.NoteBookUseCases
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
class SearchViewModel @Inject constructor(
    private val noteBookUseCases : NoteBookUseCases,
)  : ViewModel() {

    private val _state = mutableStateOf(SearchScreenState())
    val state : State<SearchScreenState> = _state

    private val _selectedNoteBook: MutableStateFlow<NoteBookWithNotes?> = MutableStateFlow(null)
    val selectedNoteBook: StateFlow<NoteBookWithNotes?> = _selectedNoteBook

    private val _uiEvents = Channel<JotSpotEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun onEvents(events: SearchEvents){
        when(events){
            is SearchEvents.GetNoteBookWithNotes -> {
                viewModelScope.launch {
                    noteBookUseCases.getAllNotesByNoteBookIdUseCase(events.noteBookId).collect{  noteBookWithNotes ->
                        _selectedNoteBook.value = noteBookWithNotes
                    }
                }
            }
            is SearchEvents.OnNoteClick -> {
                viewModelScope.launch {
                    _uiEvents.send(JotSpotEvents.Navigate(Screens.EditNote.route + "?noteId=${events.noteEntity.id}"))
                }
            }
            is SearchEvents.OnQueryChange -> {
               _state.value = _state.value.copy(
                   query = events.query
               )
            }
            SearchEvents.OnQueryDatabase -> TODO()
            SearchEvents.PopBackStack -> {
                viewModelScope.launch {
                    _uiEvents.send(JotSpotEvents.PopBackStack)
                }
            }
            is SearchEvents.UpdateSearchScreenState -> {
                viewModelScope.launch {
                    if (events.noteBookWithNotes != null){
                        _state.value.noteBookId = events.noteBookWithNotes.noteBook.noteBookId
                        _state.value.title = events.noteBookWithNotes.noteBook.title
                        _state.value.notes = events.noteBookWithNotes.notes

                    } else {
                        _state.value.notes = emptyList()
                        _state.value.noteBookId = 0
                        _state.value.title = ""
                    }
                }
            }
        }
    }
}


sealed class SearchEvents{

    data class OnQueryChange(val query: String) : SearchEvents()

    data class GetNoteBookWithNotes(val noteBookId: Int) : SearchEvents()

    data object PopBackStack : SearchEvents()

    data class OnNoteClick(val noteEntity: NoteEntity) : SearchEvents()

    data object OnQueryDatabase : SearchEvents()

    data class UpdateSearchScreenState(val noteBookWithNotes: NoteBookWithNotes?) : SearchEvents()

}

data class SearchScreenState(
    var notes : List<NoteEntity> = emptyList(),
    var title : String = "",
    var noteBookId : Int = 0,
    val query : String = ""
)