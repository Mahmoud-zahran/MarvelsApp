package com.example.marvelapp.home.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.marvelapp.ui.theme.MarvelAppTheme
import com.example.marvelapp.home.viewmodel.MarvelCharactersViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MarvelAppScreen(
    viewModel: MarvelCharactersViewModel = viewModel(),
    windowSize: WindowSizeClass,
    navController: NavController
) {
    val marvelCharactersState = viewModel.marvelCharacters.collectAsState(initial = null)
    // Get the current navigation destination
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    MarvelAppTheme {
        Scaffold(
            topBar = {
                if (currentDestination != "character_details/{characterId}") {
                    MarvelTopBar()
                }
            }
        ) { padding ->
            marvelCharactersState.value?.let { marvelCharacterResponse ->
                if (marvelCharacterResponse.data.results.isNotEmpty()) {
                    val characters = marvelCharacterResponse.data.results
                    // Setting up NavHost for navigation
                    NavHost(
                        navController = navController as NavHostController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            MarvelCharacterListScreen(
                                marvelCharacters = characters,
                                navController = navController,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(padding)
                            )
                        }
                        composable(
                            "character_details/{characterId}",
                            arguments = listOf(navArgument("characterId") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
                            val character = characters.firstOrNull { it.id == characterId }
                            character?.let {
                                MarvelCharacterDetailsScreen(
                                    marvelCharacter = it,
                                    navController = navController
                                )
                            }
                        }
                    }
//                    when (windowSize.widthSizeClass) {
//                        WindowWidthSizeClass.Compact, WindowWidthSizeClass.Expanded -> {
//                            MarvelCharacterListScreen(
//
//                                marvelCharacters = characters,
//                                navController = navController,
//                                modifier = Modifier.fillMaxSize().padding(padding)
//                            )
//                        }
//                    }
                } else {
                    // Handle empty list case with a centered message
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No characters found.", style = MaterialTheme.typography.bodyLarge)

                    }
                }
            } ?: run {
                // Center-aligned CircularProgressIndicator
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
//                CircularProgressIndicator(modifier = Modifier.padding(padding))
            }
        }
    }
}
