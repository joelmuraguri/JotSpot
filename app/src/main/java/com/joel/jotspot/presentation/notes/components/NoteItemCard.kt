package com.joel.jotspot.presentation.notes.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.joel.jotspot.R
import com.joel.jotspot.data.model.NoteEntity

@Composable
fun PinnedNoteItemCard(
    noteEntity: NoteEntity,
    onNoteItemClick : (NoteEntity) -> Unit,
    onUnpinNote : (NoteEntity) -> Unit
){

    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        ),
        modifier = Modifier
            .padding(6.dp)
            .width(140.dp)
            .height(170.dp)
            .clickable {
                onNoteItemClick(noteEntity)
            }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(15.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = noteEntity.title,
                    fontWeight = FontWeight.ExtraBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier
                        .weight(1.5f)
                )
                IconButton(
                    onClick = { onUnpinNote(noteEntity) },
                    modifier = Modifier
                        .weight(0.5f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_push_pin_24),
                        contentDescription = "",
                        tint = Color(0xFFFAF5F30)
                    )
                }
            }
            Text(
                text = noteEntity.content,
                fontWeight = FontWeight.ExtraLight,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 5
            )
        }
    }
}

@Composable
fun NoteItemCard(
    noteEntity: NoteEntity,
    onNoteItemClick : (NoteEntity) -> Unit,
    modifier: Modifier
){

    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        ),
        modifier = Modifier
            .clickable {
                onNoteItemClick(noteEntity)
            }
            .fillMaxWidth()
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = noteEntity.title,
                    fontWeight = FontWeight.Bold
                )

            },
            supportingContent = {
                Text(
                    text = noteEntity.content,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        )
    }
}

