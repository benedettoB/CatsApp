package org.benedetto.catsapp.ui.view

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun DetailsScreen(navController: NavHostController) {
        MaterialTheme {
            Text(text = "Details screen")
        }
}