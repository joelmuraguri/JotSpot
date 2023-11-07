package com.joel.jotspot.presentation.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.presentation.edit.components.EditToolBar
import com.joel.jotspot.presentation.edit.components.TransparentHintTextField
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
            TransparentHintTextField(
                text = state.title,
                hint = if (noteEntity != null) "" else state.titleHint,
                onValueChange = { title ->
                     viewModel.onEvents(EditEvents.OnTitleChange(title))
                },
                onFocusChange = { focusState ->
                    viewModel.onEvents(EditEvents.ChangeTitleFocus(focusState))
                },
                isHintVisible = state.isTitleHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            TransparentHintTextField(
                text = state.content,
                hint = if (noteEntity != null) "" else state.contentHint,
                onValueChange = { content ->
                    viewModel.onEvents(EditEvents.OnContentChange(content))
                },
                onFocusChange = { focusState ->
                    viewModel.onEvents(EditEvents.ChangeContentFocus(focusState = focusState))
                },
                isHintVisible = state.isContentHintVisible,
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxHeight(),
            )
        }
    }
}

