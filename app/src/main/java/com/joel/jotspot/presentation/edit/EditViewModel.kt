package com.joel.jotspot.presentation.edit

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.jotspot.data.model.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.joel.jotspot.domain.use_case.note.NoteUseCases
import com.joel.jotspot.navigation.Screens
import com.joel.jotspot.utils.JotSpotEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val notesUseCases : NoteUseCases
)  : ViewModel() {

    private val _state = mutableStateOf(EditScreenState())
    val state : State<EditScreenState> = _state

    private val _selectedNote: MutableStateFlow<NoteEntity?> = MutableStateFlow(null)
    val selectedNote: StateFlow<NoteEntity?> = _selectedNote

    private val _uiEvents = Channel<JotSpotEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun onEvents(events: EditEvents){
        when(events){
            is EditEvents.OnContentChange -> {
                _state.value = _state.value.copy(content = events.content)
            }
            is EditEvents.OnImageChange -> {
                _state.value = _state.value.copy(image = events.image)
            }
            EditEvents.OnLaunchBottomSheet -> TODO()
            EditEvents.OnSaveNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val note = NoteEntity(
                        title = _state.value.title,
                        content = _state.value.content,
                        image = _state.value.image,
                        timeStamp = System.currentTimeMillis()
                    )
                    notesUseCases.insertNoteUseCase(note)
                    _uiEvents.send(JotSpotEvents.Navigate(Screens.Home.route))
                }
            }
            EditEvents.OnSaveTag -> TODO()
            is EditEvents.OnTagChange -> TODO()
            is EditEvents.OnTitleChange -> {
                _state.value = _state.value.copy(title = events.title)
            }
            EditEvents.OnUpdateNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val note = NoteEntity(
                        title = _state.value.title,
                        content = _state.value.content,
                        image = _state.value.image,
                        timeStamp = System.currentTimeMillis()
                    )
                    notesUseCases.updateNotesUseCase(note)
                    _uiEvents.send(JotSpotEvents.Navigate(Screens.Home.route))
                }
            }
            is EditEvents.UpdateEditFields -> {
                viewModelScope.launch{
                    if (events.noteEntity != null){
                        _state.value.id = events.noteEntity.id
                        _state.value.title = events.noteEntity.title
                        _state.value.content = events.noteEntity.content
                        _state.value.image = events.noteEntity.image
                        _state.value.timeStamp = events.noteEntity.timeStamp
                    } else {
                        _state.value.id = 0
                        _state.value.title = ""
                        _state.value.content = ""
                        _state.value.image = null
                        _state.value.timeStamp = 0L
                    }
                }
            }
            is EditEvents.GetSelectedNote -> {
                viewModelScope.launch {
                    notesUseCases.getNoteByIdUseCase(events.id).collect{ note ->
                        _selectedNote.value = note
                    }
                }
            }

            EditEvents.OnNavBack -> {
                viewModelScope.launch {
                    _uiEvents.send(JotSpotEvents.Navigate(Screens.Home.route))
                }
            }
        }
    }
}

data class EditScreenState(
    var id : Int = 0,
    var title : String = "",
    var content : String = "",
    var image : Uri? = null,
    var timeStamp : Long = 0L
)

sealed class EditEvents{

    data class OnTitleChange(val title : String) : EditEvents()
    data class OnContentChange(val content : String) : EditEvents()
    data class OnImageChange(val image : Uri) : EditEvents()
    data class OnTagChange(val tag : String) : EditEvents()
    data class UpdateEditFields(val noteEntity: NoteEntity?) : EditEvents()
    data object OnSaveNote : EditEvents()
    data object OnUpdateNote : EditEvents()
    data object OnSaveTag : EditEvents()
    data object OnLaunchBottomSheet : EditEvents()
    data class GetSelectedNote(val id : Int) : EditEvents()
    data object OnNavBack : EditEvents()

}