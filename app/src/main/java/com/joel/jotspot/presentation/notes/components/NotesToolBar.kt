package com.joel.jotspot.presentation.notes.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesToolBar(
    title : String,
    popBackStack : () -> Unit,
    notesSize : Int
){

    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(onClick = { popBackStack() }) {
                Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
            }
        },
        title = { Text(text = title) },
        actions = {
            Text(
                text = "$notesSize",
                fontWeight = FontWeight.ExtraBold
            )
        },
        modifier = Modifier
            .padding(end = 20.dp)
    )
}