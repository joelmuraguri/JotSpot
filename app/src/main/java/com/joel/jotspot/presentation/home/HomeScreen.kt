package com.joel.jotspot.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.joel.jotspot.R
import com.joel.jotspot.data.model.NoteEntity
import com.joel.jotspot.utils.JotSpotEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onNavigate : (JotSpotEvents.Navigate) -> Unit,
    popBackStack : () -> Unit
){


    val allNotes = homeViewModel.notes.collectAsState(initial = emptyList())

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val isCollapsed = remember  { derivedStateOf { scrollBehavior.state.collapsedFraction > 0.7} }

    LaunchedEffect(key1 = true){
        homeViewModel.uiEvents.collect{ event ->
            when(event){
                is JotSpotEvents.Navigate -> {
                    onNavigate(event)
                }
                JotSpotEvents.PopBackStack -> {
                    popBackStack()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            HomeFAB( onAddNoteClick = {
                homeViewModel.onEvents(HomeEvents.OnAddNoteClick)
            })
        },
        topBar = {
            Box {
                if (isCollapsed.value) {
                    TopAppBar(
                        title = { 
                            Text(text = "")
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent
                        ),
                        actions = {
                            IconButton(onClick = {
                                homeViewModel.onEvents(HomeEvents.OnSearchClick)
                            }) {
                                Icon(imageVector = Icons.Default.Search, contentDescription = null)
                            }
                            IconButton(onClick = {
                                homeViewModel.onEvents(HomeEvents.OnAvatarClick)
                            }) {
                                Icon(imageVector = Icons.Default.Person, contentDescription = null)
                            }
                        }
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(),
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )
                }
                LargeTopAppBar(
                    title = {
                        Text(
                            text = "Good Evening, Joel",
                            fontSize = 16.sp
                        )
                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = Color.Transparent,
                    ),
                    actions = {
                        IconButton(onClick = {
                            homeViewModel.onEvents(HomeEvents.OnAvatarClick)
                        }) {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null)
                        }
                    }
                )
            }
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->

//        LazyColumn(
//            modifier = Modifier
//                .padding(paddingValues)
//        ){
//            item{
//                if (allNotes.value.isEmpty()){
//                    HomeEmptyContents()
//                } else {
//                    LazyVerticalGrid(
//                        columns = GridCells.Fixed(2),
//                        modifier = Modifier
//                    ){
//                        items(allNotes.value){ note ->
//                            NoteItem(
//                                onNoteClick = {
//                                    homeViewModel.onEvents(HomeEvents.OnNoteClick(note))
//                                },
//                                noteEntity = note,
//                            )
//                        }
//                    }
//                }
//            }
//        }

        HandleHomeContents(
            notes = allNotes.value,
            onNoteClick = { note ->
                homeViewModel.onEvents(HomeEvents.OnNoteClick(note))
            },
            modifier = Modifier
                .padding(paddingValues)
        )
    }
}

@Composable
fun HandleHomeContents(
    notes : List<NoteEntity>,
    onNoteClick: (note : NoteEntity) -> Unit,
    modifier: Modifier
){


    if (notes.isEmpty()){
        HomeEmptyContents(modifier)
    } else{
        HomeNotesContents(
            notesList = notes,
            onNoteClick = onNoteClick,
            modifier = modifier
        )
    }
}

@Composable
fun HomeEmptyContents(
    modifier: Modifier = Modifier
){

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.empty_list))


    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(24.dp)
            .fillMaxSize()
    ) {
        LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever)
    }
}


@Composable
fun HomeNotesContents(
    notesList : List<NoteEntity>,
    onNoteClick : (note : NoteEntity) -> Unit,
    modifier: Modifier = Modifier
){

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
    ){
        items(notesList){ note ->
            NoteItem(
                onNoteClick = onNoteClick,
                noteEntity = note,
                modifier = modifier
            )
        }
    }
}


@Composable
fun NoteItem(
    onNoteClick : (note : NoteEntity) -> Unit,
    noteEntity: NoteEntity,
    modifier: Modifier = Modifier
){

    Card(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 5.dp
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .padding(horizontal = 12.dp)
            .clickable { onNoteClick(noteEntity) }
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Text(
                text = noteEntity.title,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = noteEntity.content,
                fontWeight = FontWeight.Light,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = noteEntity.timeStamp.toString(),
                fontWeight = FontWeight.ExtraLight,
            )
        }
    }
}

@Composable
fun HomeFAB(
    onAddNoteClick : () -> Unit,
){
    FloatingActionButton(onClick = {
        onAddNoteClick()
    }) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
            )
            Text(
                text = "Add Note",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }
    }
}