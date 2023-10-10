package com.joel.jotspot.navigation

const val NOTE_ARGUMENT_KEY = "noteId"
const val NOTE_BOOK_ARGUMENT_KEY = "noteBookId"

sealed class Screens(val route : String) {

    data object Onboarding : Screens("onboarding_route")
    data object Home : Screens("home_route")
    data object NoteScreen : Screens("notes_screen_route")
    data object EditNote : Screens("edit_note_route")
    data object Search : Screens("search_route")
    data object Tags : Screens("tags_route")
    data object Profile : Screens("profile_route")

}