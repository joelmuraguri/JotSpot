package com.joel.jotspot.presentation.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.presentation.edit.components.EditTextFields
import com.joel.jotspot.presentation.edit.components.EditToolBar
import com.joel.jotspot.utils.JotSpotEvents

@Composable
fun EditScreen(
    viewModel: EditViewModel,
    noteEntity: NoteEntity?,
    onNavigate : (JotSpotEvents.Navigate) -> Unit,
    popBackStack : () -> Unit
){

    LaunchedEffect(key1 = true){
        viewModel.uiEvents.collect{ jotSpotEvents ->
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

    val state = viewModel.state.value

    Scaffold(
        topBar = {
            EditToolBar(
                noteEntity = noteEntity,
                onMoreOptions = {

                },
                onNavBack = {
                    viewModel.onEvents(EditEvents.OnNavBack)
                },
                onSaveClick = {
                    viewModel.onEvents(EditEvents.OnSaveNote)
                },
                onUpdateClick = {
                    viewModel.onEvents(EditEvents.OnUpdateNote)
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            EditTextFields(value = state.title,
                onValueChange = {
                       viewModel.onEvents(EditEvents.OnTitleChange(it))
                },
                maxLines = 1,
                placeholder = "Title",
                modifier = Modifier
                    .fillMaxWidth()
            )
            EditTextFields(
                value = state.content,
                onValueChange = {
                      viewModel.onEvents(EditEvents.OnContentChange(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(),
                placeholder = "Start writing from here...",
                maxLines = 100
            )
        }
    }
}

