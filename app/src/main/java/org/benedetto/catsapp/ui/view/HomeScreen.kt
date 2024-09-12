package org.benedetto.catsapp.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import org.benedetto.catsapp.ui.viewmodel.CatViewModel
import org.benedetto.catsapp.ui.viewmodel.DbViewModel
import org.benedetto.data.model.Cat
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: CatViewModel,
    dbViewModel: DbViewModel = hiltViewModel() // Inject DbViewModel
) {

    LaunchedEffect(Unit) {
        viewModel.fetchCats()
        dbViewModel.loadFavoriteCats() // Load favorite cats when the screen is loaded
    }

    MaterialTheme {
        CatScreen(navController, viewModel, dbViewModel)
    }
}

@Composable
fun CatListItem(
    cat: Cat,
    isFavorite: Boolean,  // Add isFavorite to determine whether to show the heart icon
    onClick: () -> Unit,
    onAddToFavorites: () -> Unit  // Add the action to add to favorites
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = rememberImagePainter(data = cat.url),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        cat.id?.let { Text(text = it, style = MaterialTheme.typography.bodyMedium) }

        Spacer(modifier = Modifier.weight(1f))

        // Show heart icon if the cat is a favorite
        if (isFavorite) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite",
                tint = Color.Red
            )
        }

        // Button to add the cat to favorites
        IconButton(onClick = onAddToFavorites) {
            Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Add to Favorites")
        }
    }
}


@Composable
fun CatList(
    navController: NavHostController,
    cats: List<Cat>,
    favoriteCatIds: List<String>,  // Add favoriteCatIds as a parameter
    onLoadMore: () -> Unit,
    onAddToFavorites: (String) -> Unit // Add to favorites action
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cats) { index, cat ->
                if (index > 0) {
                    HorizontalDivider(thickness = 1.dp)
                }
                CatListItem(
                    cat = cat,
                    isFavorite = favoriteCatIds.contains(cat.id ?: ""),  // Check if the cat is favorite
                    onClick = {
                        val encodedImageUrl =
                            URLEncoder.encode(cat.url, StandardCharsets.UTF_8.toString())
                        navController.navigate("details/${cat.id}/$encodedImageUrl")
                    },
                    onAddToFavorites = { cat.id?.let { onAddToFavorites(it) } } // Add to favorites
                )
            }
        }

        FloatingActionButton(
            onClick = onLoadMore,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text(text = "Load More")
        }
    }
}

@Composable
fun CatScreen(
    navController: NavHostController,
    viewModel: CatViewModel,
    dbViewModel: DbViewModel
) {
    val catState by viewModel.catState.collectAsState()
    val favoriteCats by dbViewModel.favoriteCatIds.collectAsState()

    CatList(
        navController = navController,
        cats = catState,
        favoriteCatIds = favoriteCats,  // Pass the favorite cat IDs
        onLoadMore = { viewModel.fetchCats() },
        onAddToFavorites = { catId -> dbViewModel.addToFavorites(catId) } // Add to favorites
    )
}