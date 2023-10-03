package com.joel.jotspot.presentation.edit.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.joel.jotspot.data.model.NoteEntity

@Composable
fun EditToolBar(
    noteEntity: NoteEntity?,
    onMoreOptions: () -> Unit,
    onSaveClick: () -> Unit,
    onNavBack: () -> Unit,
    onUpdateClick : () -> Unit
){

    if (noteEntity == null){
        NewNoteToolBar(
            onClose = { onNavBack() },
            onSaveClick = { onSaveClick() },
            onMoreOptions = {
                onMoreOptions()
            }
        )
    } else {
        ExistingNoteToolBar(
            onNavBack = { onNavBack() },
            onUpdateClick = { onUpdateClick() },
            onMoreOptions = { onMoreOptions()}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewNoteToolBar(
    onClose : () -> Unit,
    onSaveClick : () -> Unit,
    onMoreOptions : () -> Unit,
){

    TopAppBar(
        title = { /*TODO*/ },
        navigationIcon = {
            IconButton(onClick = { onClose() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { onMoreOptions() }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }
            IconButton(onClick = { onSaveClick() }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null)
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistingNoteToolBar(
    onNavBack : () -> Unit,
    onUpdateClick : () -> Unit,
    onMoreOptions : () -> Unit,
){

    TopAppBar(
        title = { /*TODO*/ },
        navigationIcon = {
            IconButton(onClick = { onNavBack() }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { onMoreOptions() }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }
            IconButton(onClick = { onUpdateClick() }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null)
            }
        }
    )

}
