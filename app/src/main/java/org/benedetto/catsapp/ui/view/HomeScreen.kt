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
    catViewModel: CatViewModel = hiltViewModel(),
    dbViewModel: DbViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        catViewModel.fetchCats()
        dbViewModel.loadFavoriteCats() // Load favorite cats when the screen is loaded
    }

    MaterialTheme {
        CatScreen(navController, catViewModel, dbViewModel)
    }
}

@Composable
fun CatListItem(
    cat: Cat,
    isFavorite: Boolean,  // Add isFavorite to determine whether to show the heart icon
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit  // Toggle favorite instead of only adding
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

        // Show only one heart icon based on whether the cat is a favorite
        IconButton(onClick = onToggleFavorite) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites",
                tint = if (isFavorite) Color.Red else Color.Black
            )
        }
    }
}


@Composable
fun CatList(
    navController: NavHostController,
    cats: List<Cat>,
    favoriteCatIds: List<String>,  // Add favoriteCatIds as a parameter
    onLoadMore: () -> Unit,
    onToggleFavorite: (String) -> Unit  // Add to toggle favorites
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
                    onToggleFavorite = { cat.id?.let { onToggleFavorite(it) } }  // Toggle favorite status
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
        onToggleFavorite = { catId -> dbViewModel.toggleFavorite(catId) } // Toggle favorites
    )
}