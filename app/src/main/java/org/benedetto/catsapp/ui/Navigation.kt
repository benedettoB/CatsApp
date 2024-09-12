package org.benedetto.catsapp.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.benedetto.catsapp.ui.view.CatDetailsScreen
import org.benedetto.catsapp.ui.view.HomeScreen
import org.benedetto.catsapp.ui.viewmodel.CatViewModel

@Composable
fun Navigation (){
    val navController = rememberNavController()
    val catViewModel: CatViewModel = viewModel()
    MaterialTheme{
        NavHost(navController = navController , startDestination = "home"){
            composable("home"){ HomeScreen(navController, catViewModel)}
            // Detail screen with route parameters for catId and catImageUrl
            composable(
                route = "details/{catId}/{catImageUrl}",
                arguments = listOf(
                    navArgument("catId") { type = NavType.StringType },
                    navArgument("catImageUrl") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val catId = backStackEntry.arguments?.getString("catId")
                val catImageUrl = backStackEntry.arguments?.getString("catImageUrl")

                CatDetailsScreen(catId = catId, catImageUrl = catImageUrl)
            }
        }
    }
}