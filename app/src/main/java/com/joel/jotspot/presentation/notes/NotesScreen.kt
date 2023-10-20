package com.joel.jotspot.presentation.notes

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.joel.jotspot.data.model.NoteBookEntity
import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.data.relations.NoteBookWithNotes
import com.joel.jotspot.presentation.notes.components.NotesToolBar
import com.joel.jotspot.utils.JotSpotEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    notesViewModel: NotesViewModel,
    onNavigate : (JotSpotEvents.Navigate) -> Unit,
    popBackStack : () -> Unit,
){

    val state = notesViewModel.state.value

    LaunchedEffect(key1 = true){
        notesViewModel.uiEvents.collect{ jotSpotEvents ->
            when(jotSpotEvents){
                is JotSpotEvents.Navigate -> {
                    onNavigate(jotSpotEvents)
                }
                JotSpotEvents.PopBackStack -> {
                    popBackStack()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            NotesToolBar(
                title = state.title,
                popBackStack = {
                     notesViewModel.onEvents(NoteScreenEvents.PopBackStack)
                },
                notesSize = state.notes.size
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                notesViewModel.onEvents(NoteScreenEvents.AddNewNote)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                "Notes Screen",
                fontSize = 25.sp
            )
        }
    }
}


@Composable
fun HandleNotesState(){

}

@Composable
fun HomeComponents(){

    Column {

    }

}

@Composable
fun PinnedNoteItems(){

    LazyRow{

    }

}

@Composable
fun LazyNoteItems(){

    LazyColumn{

    }

}

@Composable
fun NoteItem(
    context: Context,
    noteEntity: NoteEntity
){

}