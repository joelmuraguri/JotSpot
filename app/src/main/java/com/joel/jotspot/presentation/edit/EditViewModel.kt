package com.joel.jotspot.presentation.edit

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.joel.jotspot.domain.use_case.note.NoteUseCases

class EditViewModel(
    private val notesUseCases : NoteUseCases
)  : ViewModel() {


    var id by mutableIntStateOf(0)
        private set
    var title by mutableStateOf("")
        private set
    var content by mutableStateOf("")
        private set
    var image by mutableStateOf<Uri?>(null)
        private set


    fun onEvents(events: EditEvents){
        when(events){
            is EditEvents.OnContentChange -> {
                content = events.content
            }
            is EditEvents.OnImageChange -> {
                image = events.image
            }
            EditEvents.OnLaunchBottomSheet -> TODO()
            EditEvents.OnSaveNote -> TODO()
            EditEvents.OnSaveTag -> TODO()
            is EditEvents.OnTagChange -> TODO()
            is EditEvents.OnTitleChange -> {
                title = events.title
            }
        }
    }

    fun handleDatabaseActions(){


    }

    private fun addNote(){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}

data class EditScreenState(
    val title : String
)

sealed class EditEvents{

    data class OnTitleChange(val title : String) : EditEvents()
    data class OnContentChange(val content : String) : EditEvents()
    data class OnImageChange(val image : Uri) : EditEvents()
    data class OnTagChange(val tag : String) : EditEvents()
    data object OnSaveNote : EditEvents()
    data object OnSaveTag : EditEvents()
    data object OnLaunchBottomSheet : EditEvents()

}