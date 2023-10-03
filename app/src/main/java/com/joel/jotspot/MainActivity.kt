package com.joel.jotspot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.joel.jotspot.navigation.JotSpotNavHost
import com.joel.jotspot.presentation.home.HomeScreen
import com.joel.jotspot.presentation.home.HomeViewModel
import com.joel.jotspot.ui.theme.JotSpotTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JotSpotTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    val homeViewModel = hiltViewModel<HomeViewModel>()
//                    HomeScreen(homeViewModel = homeViewModel, onNavigate = {}) {
//
//                    }
                    JotSpotApp()
                }
            }
        }
    }
}

@Composable
fun JotSpotApp(){

    val navController = rememberNavController()

    JotSpotNavHost(navController = navController)

}

