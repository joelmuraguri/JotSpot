package com.joel.jotspot.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.jotspot.data.model.NoteBookEntity
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
class NotesViewModel @Inject constructor(
    private val noteBookUseCases : NoteBookUseCases
) : ViewModel() {

    private val _state = mutableStateOf(NoteScreenState())
    val state : State<NoteScreenState> = _state

    private val _selectedNoteBook: MutableStateFlow<NoteBookWithNotes?> = MutableStateFlow(null)
    val selectedNoteBook: StateFlow<NoteBookWithNotes?> = _selectedNoteBook

    private val _uiEvents = Channel<JotSpotEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun onEvents(events: NoteScreenEvents){
        when(events){
            is NoteScreenEvents.GetSelectedNoteBook -> {
                viewModelScope.launch {
                    noteBookUseCases.getAllNotesByNoteBookIdUseCase(events.id).collect{  noteBookWithNotes ->
                        _selectedNoteBook.value = noteBookWithNotes
                    }
                }
            }
            is NoteScreenEvents.OnNoteClick -> TODO()
            is NoteScreenEvents.OnQueryChange -> {
                _state.value = _state.value.copy(query = events.query)
            }
            is NoteScreenEvents.PinNote -> TODO()
            NoteScreenEvents.AddNewNote -> {
                viewModelScope.launch {
                    _uiEvents.send(JotSpotEvents.Navigate(Screens.EditNote.route))
                }
            }
            NoteScreenEvents.PopBackStack -> {
                viewModelScope.launch {
                    _uiEvents.send(JotSpotEvents.PopBackStack)
                }
            }
            is NoteScreenEvents.OnUpdateNoteScreenState -> {
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
            is NoteScreenEvents.OnActiveChange -> {
                _state.value = _state.value.copy(active = events.active)
            }
        }
    }
}

data class NoteScreenState(
    var notes : List<NoteEntity> = emptyList(),
    var title : String = "",
    var noteBookId : Int = 0,
    val query: String  = "",
    val active : Boolean = false
)

sealed class NoteScreenEvents {

    data class OnNoteClick(val noteEntity: NoteEntity) : NoteScreenEvents()

    data class PinNote(val isPinned : Boolean) : NoteScreenEvents()

    data class OnQueryChange(val query : String) : NoteScreenEvents()

    data class OnActiveChange(val active: Boolean) : NoteScreenEvents()

    data class GetSelectedNoteBook(val id : Int) : NoteScreenEvents()

    data object AddNewNote : NoteScreenEvents()

    data object PopBackStack : NoteScreenEvents()

    data class OnUpdateNoteScreenState(val noteBookWithNotes: NoteBookWithNotes?) : NoteScreenEvents()

}

