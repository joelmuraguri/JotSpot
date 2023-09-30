package com.joel.jotspot.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.joel.jotspot.R
import com.joel.jotspot.utils.JotSpotEvents

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    onNavigate : (JotSpotEvents.Navigate) -> Unit,
    popBackStack : () -> Unit
){

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
            FloatingActionButton(onClick = {
                homeViewModel.onEvents(HomeEvents.OnAddNoteClick)
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

        Box(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                "HOME",
                fontSize = 25.sp
            )
        }
    }
}