package com.example.dogappv2

import DogBreedsViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
/*
@Composable
fun DogBreedsScreen(
    viewModel: DogBreedsViewModel,
    onBreedClick: (String) -> Unit,
    navController: NavController
) {
    val breeds by viewModel.dogBreeds.collectAsState()
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (breeds.isEmpty()) {
            Text("Loading...")
        } else {
            DogBreedsList(breeds = breeds, onClick = onBreedClick)
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(Color.Black),
        verticalAlignment = Alignment.Bottom
    ) {
        Button(onClick = {})
        { Text("BUTTON") }
    }
    /*
    Button(
        onClick = { navController.navigate("favorites") }
    ) {
        Text("Go to Favorites")
    }
     */
}

 */
@Composable
fun DogBreedsScreen(
    viewModel: DogBreedsViewModel,
    onBreedClick: (String) -> Unit,
    navController: NavController
) {
    val breeds by viewModel.dogBreeds.collectAsState()

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (breeds.isEmpty()) {
                Text("Loading...")
            } else {
                DogBreedsList(breeds = breeds, onClick = onBreedClick)
            }
        }

        Button(
            onClick = { navController.navigate("favorites") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("Go to Favorites")
        }
    }
}

@Composable
fun DogBreedsList(breeds: Map<String, List<String>>, onClick: (String) -> Unit) {
    LazyColumn {
        items(breeds.size) { i ->
            val breedName = breeds.keys.toList()[i]
            Text(
                text = breedName,
                style = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(breedName) }
                    .border(1.dp, MaterialTheme.colorScheme.secondary, RectangleShape )
                    .padding(16.dp)
            )
        }
    }
}
