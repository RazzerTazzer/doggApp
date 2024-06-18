package com.example.dogappv2

import DogBreedsViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter


@Composable
fun BreedImagesScreen(viewModel: DogBreedsViewModel, breedName: String) {
    val images by viewModel.breedImages.collectAsState()

    if (images.isEmpty()) {
        Text("Loading...")
    } else {
        BreedImagesList(
            images = images,
            breedName = breedName,
            isFavorited = { imageUrl, breed -> viewModel.isFavorited(imageUrl, breed) },
            onFavoriteClick = { imageUrl, breed ->
                if (viewModel.isFavorited(imageUrl, breed)) {
                    viewModel.removeFromFavorite(imageUrl, breed)
                } else {
                    viewModel.addFavoriteImage(imageUrl, breed)
                }
            }
        )
    }
}

@Composable
fun BreedImagesList(
    images: List<String>,
    breedName: String,
    isFavorited: (String, String) -> Boolean,
    onFavoriteClick: (String, String) -> Unit
) {
    LazyColumn {
        items(images.size) { i ->
            val imageUrl = images[i]
            var isLiked by remember { mutableStateOf(isFavorited(images[i], breedName)) }

            LaunchedEffect(isFavorited) {
                isLiked = isFavorited(imageUrl, breedName)
            }

            Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(images[i]),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                )
                IconButton(
                    onClick = {
                        onFavoriteClick(images[i], breedName)
                        isLiked = !isLiked
                              },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewBreedImagesScreen() {
    val viewModel: DogBreedsViewModel = viewModel()
    BreedImagesScreen(viewModel, "hound")
}
