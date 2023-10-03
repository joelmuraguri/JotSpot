package com.joel.jotspot.presentation.edit.components

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EditTextFields(
    value : String,
    onValueChange : (String) -> Unit,
    modifier : Modifier = Modifier,
    maxLines : Int = 1,
    placeholder : String
){

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        maxLines = maxLines,
        placeholder = {
            Text(text = placeholder)
        }
    )

}