package com.joel.jotspot.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun NoteBookDialog(
    onDismissRequest : () -> Unit,
    value : String,
    onValueChange : (String) -> Unit,
    onSaveNoteBook : () -> Unit,

){


    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {

        Card(
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 5.dp
            ),
            modifier = Modifier
                .padding(10.dp)
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    "Create NoteBook",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    maxLines = 1,
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .fillMaxWidth(),
                    label = {
                        Text(text = "NoteBook Title")
                    },
                    shape = RoundedCornerShape(15.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Button(
                        onClick = {
                             onDismissRequest()
                        },
                        colors = ButtonDefaults.buttonColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                        ,
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                    Button(
                        onClick = {
                           onSaveNoteBook()
                        },
                        colors = ButtonDefaults.buttonColors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = CircleShape
                    ) {
                        Text(
                            text = "Save",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }
        }
    }
}
