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
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import org.benedetto.catsapp.ui.viewmodel.CatViewModel
import org.benedetto.data.model.Cat
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(navController: NavHostController, viewModel: CatViewModel) {

    LaunchedEffect(Unit) {
        viewModel.fetchCats()
    }
    MaterialTheme {
        CatScreen(navController, viewModel)
    }
}


@Composable
fun CatListItem(cat: Cat, onClick: () -> Unit) {
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
    }
}


@Composable
fun CatList(navController: NavHostController, cats: List<Cat>, onLoadMore: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cats) { index, cat ->
                if (index > 0) {
                    HorizontalDivider(thickness = 1.dp)
                }
                CatListItem(cat = cat) {
                    val encodedImageUrl =
                        URLEncoder.encode(cat.url, StandardCharsets.UTF_8.toString())
                    navController.navigate("details/${cat.id}/$encodedImageUrl")
                }
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
fun CatScreen(navController: NavHostController, viewModel: CatViewModel) {
    val catState by viewModel.catState.collectAsState()

    CatList(navController = navController, cats = catState, onLoadMore = { viewModel.fetchCats() })
}

