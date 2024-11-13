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

    val comics = viewModel.comics.collectAsState(initial = null)
    val events = viewModel.events.collectAsState(initial = null)
    val stories = viewModel.stories.collectAsState(initial = null)
    val series = viewModel.series.collectAsState(initial = null)
    MarvelAppTheme {
        Scaffold(
            topBar = {
                if (currentDestination == "home") {
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
                                    it.id,
                                    marvelCharacter = it,
                                    navController = navController, viewModel = viewModel
                                )
                            }
                        }

                        composable("comic_images/{comicId}") { backStackEntry ->
                            comics.value?.let { comic ->
                                if (comic.data.results.isNotEmpty()) {
                                    val comics = comic.data.results
                                    val comicId = backStackEntry.arguments?.getString("comicId")
                                        ?.toIntOrNull() ?: 0
                                    val sectionItem = comics.firstOrNull { it.id == comicId }

                                    sectionItem?.let {
                                        MarvelSectionImagesScreen(
                                            navController = navController,
                                            sectionItem = it
                                        )
                                    }
                                }
                            }
                        }


                        composable("event_images/{eventId}") { backStackEntry ->
                            events.value?.let { event ->
                                if (event.data.results.isNotEmpty()) {
                                    val events = event.data.results
                                    val eventId = backStackEntry.arguments?.getString("eventId")
                                        ?.toIntOrNull() ?: 0
                                    val sectionItem = events.firstOrNull { it.id == eventId }

                                    sectionItem?.let {
                                        MarvelSectionImagesScreen(
                                            navController = navController,
                                            sectionItem = it
                                        )
                                    }
                                }
                            }
                        }


                        composable("series_images/{seriesId}") { backStackEntry ->
                            series.value?.let { series ->
                                if (series.data.results.isNotEmpty()) {
                                    val serieses = series.data.results
                                    val seriesId = backStackEntry.arguments?.getString("seriesId")
                                        ?.toIntOrNull() ?: 0
                                    val sectionItem = serieses.firstOrNull { it.id == seriesId }

                                    sectionItem?.let {
                                        MarvelSectionImagesScreen(
                                            navController = navController,
                                            sectionItem = it
                                        )
                                    }
                                }
                            }
                        }

                        composable("story_images/{storyId}") { backStackEntry ->
                            stories.value?.let { story ->
                                if (story.data.results.isNotEmpty()) {
                                    val stories = story.data.results
                                    val storyId = backStackEntry.arguments?.getString("storyId")
                                        ?.toIntOrNull() ?: 0
                                    val sectionItem = stories.firstOrNull { it.id == storyId }

                                    sectionItem?.let {
                                        MarvelSectionImagesScreen(
                                            navController = navController,
                                            sectionItem = it
                                        )
                                    }
                                }
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
