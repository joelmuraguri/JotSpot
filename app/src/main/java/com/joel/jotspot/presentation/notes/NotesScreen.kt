package com.joel.jotspot.presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joel.jotspot.R
import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.data.relations.NoteBookWithNotes
import com.joel.jotspot.presentation.notes.components.NoteItemCard
import com.joel.jotspot.presentation.notes.components.NotesToolBar
import com.joel.jotspot.presentation.notes.components.PinnedNoteItemCard
import com.joel.jotspot.utils.DismissBackground
import com.joel.jotspot.utils.JotSpotEvents
import com.joel.jotspot.utils.LoadingAnimation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
    notesViewModel: NotesViewModel,
    onNavigate : (JotSpotEvents.Navigate) -> Unit,
    popBackStack : () -> Unit,
    noteBookWithNotes: NoteBookWithNotes?
){

    val state = notesViewModel.state.value
    val scope = rememberCoroutineScope()

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

        if (!state.loading){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LoadingAnimation(
                    modifier = Modifier
                        .padding(paddingValues)
                )
            }
        } else {
            if (noteBookWithNotes != null) {
                HomeComponents(
                    unpinnedNotes = state.unPinnedNotes,
                    pinnedNotes = state.pinnedNotes,
                    onNoteItemClick = { note ->
                        notesViewModel.onEvents(NoteScreenEvents.OnNoteClick(note))
                    },
                    scope = scope,
                    onPinNote = { noteEntity ->
                        notesViewModel.onEvents(NoteScreenEvents.PinNote(noteId = noteEntity.id, isPinned = noteEntity.isPinned))
                    },
                    onDeleteNote = { note ->
                        notesViewModel.onEvents(NoteScreenEvents.OnDeleteNote(note))
                    },
                    modifier = Modifier
                        .padding(paddingValues),
                    onSearchClick = {
                        notesViewModel.onEvents(NoteScreenEvents.OnSearchBarClick(it))
                    },
                    onUnpinNote = { noteEntity ->
                        notesViewModel.onEvents(NoteScreenEvents.UnPinNote(noteId = noteEntity.id, isPinned = noteEntity.isPinned))
                    },
                    noteBookWithNotes = noteBookWithNotes
                )
            }
        }
    }
}

@Composable
fun HomeComponents(
    unpinnedNotes: List<NoteEntity>,
    pinnedNotes: List<NoteEntity>,
    onNoteItemClick : (NoteEntity) -> Unit,
    scope: CoroutineScope,
    onPinNote : (NoteEntity) -> Unit,
    onDeleteNote : (NoteEntity) -> Unit,
    modifier: Modifier = Modifier,
    onSearchClick: (NoteBookWithNotes) -> Unit,
    onUnpinNote: (NoteEntity) -> Unit,
    noteBookWithNotes: NoteBookWithNotes
){
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        if (pinnedNotes.isEmpty()){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painter = painterResource(id = R.drawable.add_note_book), contentDescription = null)
                    Text(text = "No Pinned Notes")
                }

            }
        } else{
            Column {
                PinnedIcon()
                PinnedNoteItems(
                    pinnedNotes = pinnedNotes,
                    onNoteItemClick = onNoteItemClick,
                    onUnpinNote = onUnpinNote
                )
            }
        }

        NotesScreenHeader(
            onSearchClick = onSearchClick,
            noteBookWithNotes = noteBookWithNotes
        )

        if (unpinnedNotes.isEmpty()){
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()

            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painter = painterResource(id = R.drawable.add_note_book), contentDescription = null)
                    Text(text = "Click On the Button To add New Notes")
                }

            }
        } else {
            LazyNoteItems(
                unpinnedNotes = unpinnedNotes,
                onNoteItemClick = onNoteItemClick,
                scope = scope,
                onPinNote = onPinNote,
                onDeleteNote = onDeleteNote
            )
        }
    }
}

@Composable
fun PinnedNoteItems(
    pinnedNotes : List<NoteEntity>,
    onNoteItemClick : (NoteEntity) -> Unit,
    onUnpinNote : (NoteEntity) -> Unit
){

    LazyRow{
        items(pinnedNotes){note ->
            PinnedNoteItemCard(
                noteEntity = note,
                onNoteItemClick = { onNoteItemClick(note) },
                onUnpinNote = onUnpinNote
            ) 
        }
    }
}

@Composable
fun LazyNoteItems(
    unpinnedNotes : List<NoteEntity>,
    onNoteItemClick : (NoteEntity) -> Unit,
    scope: CoroutineScope,
    onPinNote : (NoteEntity) -> Unit,
    onDeleteNote : (NoteEntity) -> Unit
){

    LazyColumn{
        items(unpinnedNotes){ note ->
            NoteItem(
                noteEntity = note,
                onNoteItemClick = onNoteItemClick,
                scope = scope,
                onPinNote = onPinNote,
                onDeleteNote = onDeleteNote
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteItem(
    noteEntity: NoteEntity,
    onNoteItemClick : (NoteEntity) -> Unit,
    scope: CoroutineScope,
    onPinNote : (NoteEntity) -> Unit,
    onDeleteNote : (NoteEntity) -> Unit
){
    val dismissState = rememberDismissState()
    val dismissDirection = dismissState.dismissDirection
    
    val deleteState = dismissState.isDismissed(DismissDirection.EndToStart)
    if (deleteState && dismissDirection == DismissDirection.EndToStart){
        SideEffect {
            scope.launch {
                onDeleteNote(noteEntity)
            }
        }
    }

    val pinState = dismissState.isDismissed(DismissDirection.StartToEnd)
    if (pinState && dismissDirection == DismissDirection.StartToEnd){
        SideEffect {
            scope.launch {
                onPinNote(noteEntity)
            }
        }
    }

    var itemAppeared by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true){
        itemAppeared = true
    }

    Card(
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        ),
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth()
    ){
        AnimatedVisibility(
            visible = itemAppeared && !deleteState && !pinState,
            exit = fadeOut(spring())
        ) {
            SwipeToDismiss(
                state = dismissState,
                modifier = Modifier,
                background = {
                    DismissBackground(dismissState)
                },
                dismissContent = {
                    NoteItemCard(
                        noteEntity = noteEntity,
                        onNoteItemClick = onNoteItemClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            )
        }
    }
}

@Composable
fun PinnedIcon(){

    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = Modifier
            .padding(end = 6.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Pinned",
                color = Color(0xFFFAF5F30)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Card(
                shape = RoundedCornerShape(50),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = Color(0xFFFAF5F30)
                )
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun NotesScreenHeader(
    noteBookWithNotes: NoteBookWithNotes,
    onSearchClick : (NoteBookWithNotes) -> Unit
){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "All",
                fontWeight = FontWeight.ExtraLight
            )
            IconButton(onClick = { onSearchClick(noteBookWithNotes) }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.Black.copy(0.4f)
                )
            }
        }
    }

}