package com.joel.jotspot.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.joel.jotspot.presentation.edit.EditScreen
import com.joel.jotspot.presentation.home.HomeScreen
import com.joel.jotspot.presentation.profile.ProfileScreen
import com.joel.jotspot.presentation.search.SearchScreen

@Composable
fun JotSpotNavHost(
    navController: NavHostController
){

    NavHost(
        navController = navController,
        startDestination = Screens.Home.route)
    {
        composable(route = Screens.Home.route){
            HomeScreen(
                onNavToEdit = {
                    navController.navigate(Screens.EditNote.route)
                },
                onNavToProfile = {
                    navController.navigate(Screens.Profile.route)
                },
                onNavToSearch = {
                    navController.navigate(Screens.Search.route)
                }
            )
        }
        composable(route = Screens.EditNote.route){
            EditScreen()
        }
        composable(route = Screens.Search.route){
            SearchScreen()
        }
        composable(route = Screens.Profile.route){
            ProfileScreen()
        }
    }
}