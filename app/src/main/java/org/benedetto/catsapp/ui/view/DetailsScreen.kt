package org.benedetto.catsapp.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun CatDetailsScreen(catId: String?, catImageUrl: String?) {
    MaterialTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = catId ?: "Unknown Cat", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = rememberImagePainter(data = catImageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Cat ID: $catId", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

/*
@Composable
fun CatDetailsScreen(catId: String?, catImageUrl: String?) {
    val catViewModel: CatViewModel = viewModel()
    val isFavorite = remember { mutableStateOf(catViewModel.isFavorite(catId)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display cat details (image, ID, etc.)
        catImageUrl?.let {
            Image(
                painter = rememberImagePainter(it),
                contentDescription = "Cat Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Cat ID: $catId", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(16.dp))

        // Add to favorites button
        IconButton(onClick = {
            isFavorite.value = !isFavorite.value
            if (isFavorite.value) {
                catViewModel.addCatToFavorites(catId)
            } else {
                catViewModel.removeCatFromFavorites(catId)
            }
        }) {
            Icon(
                imageVector = if (isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Add to Favorites"
            )
        }

        Text(
            text = if (isFavorite.value) "Added to favorites" else "Not in favorites",
            style = MaterialTheme.typography.body1
        )
    }
}

 */