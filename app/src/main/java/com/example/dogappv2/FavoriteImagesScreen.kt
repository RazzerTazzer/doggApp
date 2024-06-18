import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteImagesScreen(viewModel: DogBreedsViewModel) {
    val favoriteImages by viewModel.favoriteImages.collectAsState()

    var favoriteBreedsList by remember {
        mutableStateOf<List<String>>(favoriteImages.keys.toList())
    }

    var selectedBreed by remember { mutableStateOf("All") }
    var isExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(favoriteImages) {
        favoriteBreedsList = favoriteImages.keys.toList()
        if (selectedBreed != "All" && selectedBreed !in favoriteBreedsList) {
            selectedBreed = "All"
        }
    }

    Column {
        if (favoriteImages.isEmpty()) {
            Text("No favorites yet.")
        } else {
            // Breed filter dropdown
            Box(modifier = Modifier.padding(16.dp)) {
                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { isExpanded = !isExpanded }
                ) {
                    TextField(
                        value = selectedBreed,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                        },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("All") },
                            onClick = {
                                selectedBreed = "All"
                                isExpanded = false
                            }
                        )
                        favoriteBreedsList.forEach { breed ->
                            DropdownMenuItem(
                                text = { Text(text = breed) },
                                onClick = {
                                    selectedBreed = breed
                                    isExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Filtered breed images
            LazyColumn {
                favoriteImages
                    .filter { selectedBreed == "All" || it.key == selectedBreed }
                    .forEach { (breed, images) ->
                        item {
                            BreedImagesList(images = images, breedName = breed, viewModel = viewModel)
                        }
                    }
            }
        }
    }
}

@Composable
fun BreedImagesList(images: List<String>, breedName: String, viewModel: DogBreedsViewModel) {
    Column {
        images.forEach { imageUrl ->
            Text(breedName, modifier = Modifier.padding(8.dp))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                )
                IconButton(
                    onClick = { viewModel.removeFromFavorite(imageUrl, breedName) },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color.Red
                    )
                }
            }
        }
    }
}