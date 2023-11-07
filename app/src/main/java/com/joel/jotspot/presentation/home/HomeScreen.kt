package com.joel.jotspot.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.joel.jotspot.R
import com.joel.jotspot.data.model.NoteBookEntity
import com.joel.jotspot.presentation.home.components.NoteBookDialog
import com.joel.jotspot.presentation.home.components.NoteBookItem
import com.joel.jotspot.utils.JotSpotEvents
import com.joel.jotspot.utils.LoadingAnimation
import com.joel.jotspot.utils.RequestState

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigate :(JotSpotEvents.Navigate) -> Unit,
    popBackStack : () -> Unit
) {

    val state = homeViewModel.state.value

    val allNoteBooks by homeViewModel.allNoteBooks.collectAsState()

    LaunchedEffect(key1 = true) {
        homeViewModel.uiEvents.collect { event ->
            when (event) {
                is JotSpotEvents.Navigate -> {
                    onNavigate(event)
                }

                JotSpotEvents.PopBackStack -> {
                    popBackStack()
                }

            }
        }
    }


    HandleHomeScreenState(
        allNoteBooks = allNoteBooks,
        showDialog = state.showDialog,
        onDismissRequest = {
              homeViewModel.onEvents(HomeScreenEvents.DismissDialog)
        },
        value = state.noteBookTitle,
        onValueChange = { title ->
             homeViewModel.onEvents(HomeScreenEvents.OnNoteBookTitleChange(title))
        },
        onSaveNoteBook = {
             homeViewModel.onEvents(HomeScreenEvents.SaveNoteBook)
        },
        onNavToProfile = {
            homeViewModel.onEvents(HomeScreenEvents.NavToProfile)
        },
        onAddNoteBook = {
            homeViewModel.onEvents(HomeScreenEvents.AddNewNote)
        },
        onNoteBookClick = { noteBook ->
            homeViewModel.onEvents(HomeScreenEvents.OnNoteBookClick(noteBook))
        }
    )
}

@Composable
fun HandleHomeScreenState(
    allNoteBooks : RequestState<List<NoteBookEntity>>,
    showDialog : Boolean,
    onDismissRequest : () -> Unit,
    value : String,
    onValueChange : (String) -> Unit,
    onSaveNoteBook : () -> Unit,
    onNavToProfile : () -> Unit,
    onAddNoteBook: () -> Unit,
    onNoteBookClick : (NoteBookEntity) -> Unit
){

    when(allNoteBooks){
        RequestState.Idle -> TODO()
        RequestState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LoadingAnimation()
            }
        }
        is RequestState.Success -> {
            HomeContent(
                showDialog = showDialog,
                onDismissRequest = onDismissRequest,
                value = value,
                onValueChange = onValueChange,
                onSaveNoteBook = onSaveNoteBook,
                onNavToProfile = onNavToProfile,
                onAddNoteBook = onAddNoteBook,
                onNoteBookClick = onNoteBookClick,
                allNoteBooks = allNoteBooks.data
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    showDialog : Boolean,
    onDismissRequest : () -> Unit,
    value : String,
    onValueChange : (String) -> Unit,
    onSaveNoteBook : () -> Unit,
    onNavToProfile : () -> Unit,
    onAddNoteBook: () -> Unit,
    onNoteBookClick : (NoteBookEntity) -> Unit,
    allNoteBooks : List<NoteBookEntity>
 ){

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val isCollapsed = remember { derivedStateOf { scrollBehavior.state.collapsedFraction > 0.7 } }

    Scaffold(
        topBar = {
            Box {
                if (isCollapsed.value) {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Hi, Joel",
                                fontSize = 22.sp
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        actions = {
                            IconButton(onClick = {
                                onNavToProfile()
                            }) {
                                Icon(imageVector = Icons.Default.Person, contentDescription = null)
                            }
                        }
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        painter = painterResource(id = R.drawable.jotspot_image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    LargeTopAppBar(
                        title = {

                        },
                        scrollBehavior = scrollBehavior,
                        colors = TopAppBarDefaults.largeTopAppBarColors(
                            containerColor = Color.Transparent,
                        ),
                        actions = {

                        }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAddNoteBook()
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->


        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            HandleHomeContent(
                onNoteBookClick = onNoteBookClick,
                noteBookList = allNoteBooks,
            )
        }


        if (showDialog){
            NoteBookDialog(
                onDismissRequest = {
                    onDismissRequest()
                },
                value = value,
                onValueChange = { title ->
                    onValueChange(title)
                },
                onSaveNoteBook = {
                    onSaveNoteBook()
                }
            )
        }
    }
}

@Composable
fun HandleHomeContent(
    onNoteBookClick : (NoteBookEntity) -> Unit,
    noteBookList : List<NoteBookEntity>,
    modifier: Modifier = Modifier
){

    HomeNoteBookContents(
        onNoteBookClick = onNoteBookClick,
        noteBookList = noteBookList,
        modifier
    )
}

@Composable
fun EmptyHomeContents(
    modifier: Modifier
){
    
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .verticalScroll(rememberScrollState())
        ) {
            
            Image(painter = painterResource(id = R.drawable.add_note_book), contentDescription = null)
            
            Text(text = "")
            
        }
    }
}


@Composable
fun HomeNoteBookContents(
    onNoteBookClick : (NoteBookEntity) -> Unit,
    noteBookList : List<NoteBookEntity>,
    modifier: Modifier
){

    if (noteBookList.isEmpty()){
        EmptyHomeContents(modifier)
    } else {
        NoteBookLazyItems(
            onNoteBookClick = onNoteBookClick,
            noteBookList = noteBookList,
        )
    }
}


@Composable
fun NoteBookLazyItems(
    onNoteBookClick : (NoteBookEntity) -> Unit,
    noteBookList : List<NoteBookEntity>,
){

    LazyColumn{
        item {
            NoteBooksBar()
        }
        items(noteBookList){noteBookItem ->
            NoteBookItem(
                noteBookEntity = noteBookItem,
                onNoteBookClick = onNoteBookClick,
            )
        }
    }
}

@Composable
fun NoteBooksBar(){

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopStart
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "All NoteBooks",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }
        }
    }
}