package com.joel.jotspot.presentation.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.joel.jotspot.presentation.notes.components.NotesToolBar
import com.joel.jotspot.utils.JotSpotEvents

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    popBackStack : () -> Unit,
    onNavigate : (JotSpotEvents.Navigate) -> Unit
){

    val state = searchViewModel.state.value

    LaunchedEffect(key1 = true){
        searchViewModel.uiEvents.collect{ jotSpotEvents ->
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
                     searchViewModel.onEvents(SearchEvents.PopBackStack)
                },
                notesSize = state.notes.size
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                "SEARCH",
                fontSize = 25.sp
            )
        }
    }


}