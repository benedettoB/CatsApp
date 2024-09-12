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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import org.benedetto.catsapp.ui.viewmodel.DbViewModel

@Composable
fun CatDetailsScreen(
    catId: String?,
    catImageUrl: String?,
    dbViewModel: DbViewModel = hiltViewModel() // Inject DbViewModel to manage favorite status
) {
    // Make sure catId is not null
    catId?.let {
        // Observe favoriteCatIds StateFlow from DbViewModel
        val favoriteCatIds by dbViewModel.favoriteCatIds.collectAsState()

        MaterialTheme {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(text = catId, style = MaterialTheme.typography.bodyMedium)
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

                // Determine if this cat is a favorite based on catId
                val isFavorite = favoriteCatIds.contains(catId)

                // Favorite toggle button
                IconButton(onClick = { dbViewModel.toggleFavorite(catId) }) {
                    Icon(
                        imageVector = if(isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from Favorites" else "Add to Favorites",
                        tint = if (isFavorite) Color.Red else Color.Black
                    )
                }
            }
        }
    }
}