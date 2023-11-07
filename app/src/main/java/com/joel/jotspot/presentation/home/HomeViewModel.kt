package com.joel.jotspot.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.jotspot.data.model.NoteBookEntity
import com.joel.jotspot.domain.use_case.note_book.NoteBookUseCases
import com.joel.jotspot.navigation.Screens
import com.joel.jotspot.utils.JotSpotEvents
import com.joel.jotspot.utils.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val noteBookUseCases: NoteBookUseCases
) : ViewModel(){

    private val _state = mutableStateOf(HomeScreenState())
    val state : State<HomeScreenState> = _state

    private val _uiEvents = Channel<JotSpotEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private val _allNoteBooks = MutableStateFlow<RequestState<List<NoteBookEntity>>>(RequestState.Idle)
    val allNoteBooks : StateFlow<RequestState<List<NoteBookEntity>>> = _allNoteBooks

    init {
        getAllNoteBooks()
    }

    fun onEvents(events: HomeScreenEvents){
        when(events){
            HomeScreenEvents.AddNewNote -> {
                _state.value = _state.value.copy(showDialog = true)
            }
            HomeScreenEvents.NavToProfile -> {
                viewModelScope.launch {
                    _uiEvents.send(JotSpotEvents.Navigate(Screens.Profile.route))
                }
            }
            is HomeScreenEvents.OnNoteBookTitleChange -> {
                _state.value = _state.value.copy(noteBookTitle = events.noteBookTitle)
            }
            HomeScreenEvents.SaveNoteBook -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val noteBook = NoteBookEntity(
                        title = _state.value.noteBookTitle
                    )
                    noteBookUseCases.insertNoteBookUseCase(noteBook)
                }
                _state.value = _state.value.copy(showDialog = false)
            }

            is HomeScreenEvents.OnNoteBookClick -> {
                viewModelScope.launch {
                    _uiEvents.send(JotSpotEvents.Navigate(Screens.NoteScreen.route + "?noteBookId=${events.noteBookEntity.noteBookId}"))
                }
            }
            HomeScreenEvents.DismissDialog -> {
                _state.value = _state.value.copy(showDialog = false)
            }
        }
    }

    private fun getAllNoteBooks(){
        _allNoteBooks.value = RequestState.Loading
        viewModelScope.launch {
            noteBookUseCases.getAllNoteBooks().collect{ noteBooksList ->
                _allNoteBooks.value = RequestState.Success(noteBooksList)
            }
        }
    }
}

data class HomeScreenState(
    val noteBookTitle: String = "",
    val showDialog : Boolean = false
)

sealed class HomeScreenEvents {

    data object NavToProfile : HomeScreenEvents()
    data class OnNoteBookTitleChange(val noteBookTitle : String) : HomeScreenEvents()
    data object SaveNoteBook : HomeScreenEvents()
    data object AddNewNote : HomeScreenEvents()
    data object DismissDialog : HomeScreenEvents()
    data class OnNoteBookClick(val noteBookEntity: NoteBookEntity) : HomeScreenEvents()

}