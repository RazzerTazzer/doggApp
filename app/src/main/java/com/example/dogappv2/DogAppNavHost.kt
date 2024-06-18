package com.example.dogappv2

import DogBreedsViewModel
import FavoriteImagesScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun DogAppNavHost(viewModel: DogBreedsViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "breeds") {
        composable("breeds") {
            DogBreedsScreen(
                viewModel = viewModel,
                onBreedClick = { breedName ->
                    navController.navigate("breedImages/$breedName")
                },
                navController = navController // Pass the NavController here
            )
        }
        composable("breedImages/{breedName}") { backStackEntry ->
            val breedName = backStackEntry.arguments?.getString("breedName")
            if (breedName != null) {
                viewModel.fetchBreedImages(breedName)
                BreedImagesScreen(viewModel, breedName)
            }
        }
        composable("favorites") {
            FavoriteImagesScreen(viewModel)
        }
    }
}

