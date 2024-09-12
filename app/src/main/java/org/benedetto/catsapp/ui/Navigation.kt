package org.benedetto.catsapp.ui

import CatDetailsScreen
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.benedetto.catsapp.ui.view.HomeScreen
import org.benedetto.catsapp.ui.viewmodel.CatViewModel
import org.benedetto.catsapp.ui.viewmodel.DbViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val catViewModel: CatViewModel = hiltViewModel()
    val dbViewModel: DbViewModel = hiltViewModel()

    MaterialTheme {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(navController, catViewModel, dbViewModel = dbViewModel)
            }
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

                // Pass the dbViewModel to the CatDetailsScreen
                CatDetailsScreen(catId = catId, catImageUrl = catImageUrl, dbViewModel = dbViewModel)
            }
        }
    }
}
