package com.joel.jotspot.presentation.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joel.jotspot.data.model.NoteBookEntity

@Composable
fun NoteBookItem(
    noteBookEntity: NoteBookEntity,
    onNoteBookClick : (NoteBookEntity) -> Unit
){

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(horizontal = 14.dp, vertical = 15.dp)
            .clickable {
                onNoteBookClick(noteBookEntity)
            }
            .fillMaxWidth()
    ) {
        Text(
            text = noteBookEntity.title,
            modifier = Modifier
                .padding(20.dp)
        )
    }
}

