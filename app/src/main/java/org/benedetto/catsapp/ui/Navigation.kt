package org.benedetto.catsapp.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.benedetto.catsapp.ui.view.DetailsScreen
import org.benedetto.catsapp.ui.view.HomeScreen
import org.benedetto.catsapp.ui.viewmodel.CatViewModel

@Composable
fun Navigation (){
    val navController = rememberNavController()
    val catViewModel: CatViewModel = viewModel()
    MaterialTheme{
        NavHost(navController = navController , startDestination = "home"){
            composable("home"){ HomeScreen(navController, catViewModel)}
            composable("details/{cat.id}/{catImageUrl}"){ DetailsScreen(navController)}
        }
    }
}