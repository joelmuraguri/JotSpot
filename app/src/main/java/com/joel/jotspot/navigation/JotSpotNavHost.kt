package com.joel.jotspot.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joel.jotspot.presentation.edit.EditEvents
import com.joel.jotspot.presentation.edit.EditScreen
import com.joel.jotspot.presentation.edit.EditViewModel
import com.joel.jotspot.presentation.home.HomeScreen
import com.joel.jotspot.presentation.home.HomeViewModel
import com.joel.jotspot.presentation.notes.NoteScreenEvents
import com.joel.jotspot.presentation.notes.NotesScreen
import com.joel.jotspot.presentation.notes.NotesViewModel
import com.joel.jotspot.presentation.profile.ProfileScreen
import com.joel.jotspot.presentation.search.SearchEvents
import com.joel.jotspot.presentation.search.SearchScreen
import com.joel.jotspot.presentation.search.SearchViewModel

@Composable
fun JotSpotNavHost(
    navController: NavHostController,
){

    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {
        composable(route = Screens.Home.route){
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
               onNavigate = { jotSpotEvent ->
                   navController.navigate(jotSpotEvent.route)
               },
                popBackStack = {
                    navController.popBackStack()
                },
                homeViewModel = homeViewModel
            )
        }
        composable(
            route = Screens.NoteScreen.route + "?noteBookId={${NOTE_BOOK_ARGUMENT_KEY}}",
            arguments = listOf(navArgument(NOTE_BOOK_ARGUMENT_KEY){
                type = NavType.IntType
                defaultValue = -1
            })
        ){navBackStackEntry ->
            val noteViewModel = hiltViewModel<NotesViewModel>()

            val noteBookId =  navBackStackEntry.arguments!!.getInt(NOTE_BOOK_ARGUMENT_KEY)
            LaunchedEffect(key1 = noteBookId){
                noteViewModel.onEvents(NoteScreenEvents.GetSelectedNoteBook(noteBookId))
                noteViewModel.onEvents(NoteScreenEvents.GetPinnedNotes(noteBookId))
                noteViewModel.onEvents(NoteScreenEvents.GetUnPinnedNotes(noteBookId))
            }

            val selectedNoteBookWithNotes by noteViewModel.selectedNoteBook.collectAsState()
            LaunchedEffect(key1 = selectedNoteBookWithNotes){
                noteViewModel.onEvents(NoteScreenEvents.OnUpdateNoteScreenState(noteBookWithNotes = selectedNoteBookWithNotes))
            }
            NotesScreen(
                onNavigate = {jotSpotEvents ->
                    navController.navigate(jotSpotEvents.route)
                },
                popBackStack = {
                    navController.popBackStack()
                },
                notesViewModel = noteViewModel,
                noteBookWithNotes = selectedNoteBookWithNotes
            )
        }
        composable(
            route = Screens.EditNote.route + "?noteId={${NOTE_ARGUMENT_KEY}}",
            arguments = listOf(navArgument(NOTE_ARGUMENT_KEY){
                type = NavType.IntType
                defaultValue = -1
            }),
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(
                        durationMillis = 300
                    )
                )
            }
        ){ navBackStackEntry ->
            val editViewModel = hiltViewModel<EditViewModel>()

            val noteId = navBackStackEntry.arguments!!.getInt(NOTE_ARGUMENT_KEY)
            LaunchedEffect(key1 = noteId){
                editViewModel.onEvents(EditEvents.GetSelectedNote(noteId))
            }

            val selectedNote by editViewModel.selectedNote.collectAsState()
            LaunchedEffect(key1 = selectedNote){
                if (selectedNote != null || noteId == -1){
                   editViewModel.onEvents(EditEvents.UpdateEditFields(noteEntity = selectedNote))
                }
            }
            EditScreen(
                viewModel = editViewModel,
                noteEntity = selectedNote,
                onNavigate = { jotSpotEvent ->
                    navController.navigate(jotSpotEvent.route)
                },
                popBackStack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Screens.Search.route + "?noteBookId={${NOTE_BOOK_ARGUMENT_KEY}}",
            arguments = listOf(navArgument(NOTE_BOOK_ARGUMENT_KEY){
                type = NavType.IntType
                defaultValue = -1
            })
        ){navBackStackEntry ->
            val searchViewModel = hiltViewModel<SearchViewModel>()

            val noteBookId =  navBackStackEntry.arguments!!.getInt(NOTE_BOOK_ARGUMENT_KEY)
            LaunchedEffect(key1 = noteBookId){
                searchViewModel.onEvents(SearchEvents.GetNoteBookWithNotes(noteBookId))
            }

            val selectedNoteBook by searchViewModel.selectedNoteBook.collectAsState()
            LaunchedEffect(key1 = selectedNoteBook){
                searchViewModel.onEvents(SearchEvents.UpdateSearchScreenState(noteBookWithNotes = selectedNoteBook))
            }
            SearchScreen(
                onNavigate = { jotSpotEvent ->
                    navController.navigate(jotSpotEvent.route)
                },
                popBackStack = {
                    navController.popBackStack()
                },
                searchViewModel = searchViewModel
            )
        }
        composable(route = Screens.Profile.route){
            ProfileScreen()
        }
    }
}