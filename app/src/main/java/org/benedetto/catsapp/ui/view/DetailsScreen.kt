package org.benedetto.catsapp.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import org.benedetto.catsapp.ui.viewmodel.DbViewModel

@Composable
fun CatDetailsScreen(catId: String?, catImageUrl: String?,  dbViewModel: DbViewModel = hiltViewModel()) {
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

            //////////////
            if (catId != null) {
                // UI for displaying the cat details
                Column {
                    // Add to favorites button
                    IconButton(onClick = { dbViewModel.addToFavorites(catId) }) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Add to Favorites")
                    }
                }
            }
        }
    }
}