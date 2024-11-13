package com.example.marvelapp.home.ui


import android.annotation.SuppressLint
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
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.domain.model.BaseResponse
import com.example.marvelapp.ui.theme.MarvelAppTheme
import com.example.marvelapp.home.viewmodel.MarvelCharactersViewModel
import com.example.domain.model.Result
import com.example.domain.model.SectionItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MarvelAppScreen(
    viewModel: MarvelCharactersViewModel = viewModel(),
    windowSize: WindowSizeClass,
    navController: NavController
) {
    val marvelCharactersState = viewModel.marvelCharacters.collectAsState()
    val comicsState = viewModel.comics.collectAsState()
    val eventsState = viewModel.events.collectAsState()
    val storiesState = viewModel.stories.collectAsState()
    val seriesState = viewModel.series.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()

    // Get the current navigation destination
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    MarvelAppTheme {
        Scaffold(
            topBar = {
                if (currentDestination == "home") {
                    MarvelTopBar()
                }
            }
        ) { padding ->
            Box(modifier = Modifier.fillMaxSize()) {
                // Handle the characters list state
                when (val charactersResult = marvelCharactersState.value) {
                    is Result.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    is Result.Success -> {
                        if (charactersResult.data.data.results.isNotEmpty()) {
                            NavHost(
                                navController = navController as NavHostController,
                                startDestination = "home"
                            ) {
                                composable("home") {
                                    MarvelCharacterListScreen(
                                        marvelCharacters = charactersResult.data.data.results,
                                        navController = navController,
                                        loadNextPage = { viewModel.loadNextPage() },
                                        isLoading = isLoading.value,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                                composable(
                                    "character_details/{characterId}",
                                    arguments = listOf(navArgument("characterId") { type = NavType.IntType })
                                ) { backStackEntry ->
                                    val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
                                    val character = charactersResult.data.data.results.firstOrNull { it.id == characterId }
                                    character?.let {
                                        MarvelCharacterDetailsScreen(
                                            it.id,
                                            marvelCharacter = it,
                                            navController = navController,
                                            viewModel = viewModel
                                        )
                                    }
                                }

                                // Section navigation composables
//                                composable("comic_images/{comicId}") { backStackEntry ->
//                                    handleSectionNavigation(comicsState.value, backStackEntry.arguments?.getInt("comicId"), navController)
//                                }
                                composable("comic_images/{comicId}") { backStackEntry ->
                                    comicsState.value?.let { comic ->
//
                                        when (comic) {
                                            is Result.Loading -> Box(
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                            }
                                            is Result.Success -> {
                                                val comicId = backStackEntry.arguments?.getString("comicId")
                                                ?.toIntOrNull() ?: 0
                                                comic.data.data.results.firstOrNull { it.id == comicId }?.let { sectionItem ->
                                                    MarvelSectionImagesScreen(navController = navController, sectionItem = sectionItem)
                                                }
                                            }
                                            is Result.Error -> {
                                                Box(
                                                    modifier = Modifier.fillMaxSize()
                                                ) {
                                                    Text(
                                                        text = "Error: ${comic.message}",
                                                        modifier = Modifier.align(Alignment.Center),
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = MaterialTheme.colorScheme.error
                                                    )
                                                }
                                            }
                                            else -> {}
                                        }
                                    }
                                }
                                composable("event_images/{eventId}") { backStackEntry ->
                                eventsState.value?.let { event ->
//
                                        when (event) {
                                            is Result.Loading -> Box(
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                            }
                                            is Result.Success -> {
                                                val eventId = backStackEntry.arguments?.getString("eventId")
                                                    ?.toIntOrNull() ?: 0
                                                event.data.data.results.firstOrNull { it.id == eventId }?.let { sectionItem ->
                                                    MarvelSectionImagesScreen(navController = navController, sectionItem = sectionItem)
                                                }
                                            }
                                            is Result.Error -> {
                                                Box(
                                                    modifier = Modifier.fillMaxSize()
                                                ) {
                                                    Text(
                                                        text = "Error: ${event.message}",
                                                        modifier = Modifier.align(Alignment.Center),
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = MaterialTheme.colorScheme.error
                                                    )
                                                }
                                            }
                                            else -> {}
                                        }
                                    }
                                }
//                                composable("event_images/{eventId}") { backStackEntry ->
//                                    val eventId = backStackEntry.arguments?.getString("eventId")?.toIntOrNull() ?: 0
//
//                                    handleSectionNavigation(eventsState.value,eventId, navController)
//                                }
                                composable("series_images/{seriesId}") { backStackEntry ->
                                    seriesState.value?.let { series ->
//
                                        when (series) {
                                            is Result.Loading -> Box(
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                            }
                                            is Result.Success -> {
                                                val seriesId = backStackEntry.arguments?.getString("seriesId")
                                                    ?.toIntOrNull() ?: 0
                                                series.data.data.results.firstOrNull { it.id == seriesId }?.let { sectionItem ->
                                                    MarvelSectionImagesScreen(navController = navController, sectionItem = sectionItem)
                                                }
                                            }
                                            is Result.Error -> {
                                                Box(
                                                    modifier = Modifier.fillMaxSize()
                                                ) {
                                                    Text(
                                                        text = "Error: ${series.message}",
                                                        modifier = Modifier.align(Alignment.Center),
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = MaterialTheme.colorScheme.error
                                                    )
                                                }
                                            }
                                            else -> {}
                                        }
                                    }
                                }
//                                composable("series_images/{seriesId}") { backStackEntry ->
//                                    val seriesId = backStackEntry.arguments?.getString("seriesId")?.toIntOrNull() ?: 0
//
//                                    handleSectionNavigation(seriesState.value, seriesId, navController)
//                                }
                                composable("story_images/{storyId}") { backStackEntry ->
                                    storiesState.value?.let { story ->
//
                                        when (story) {
                                            is Result.Loading -> Box(
                                                modifier = Modifier.fillMaxSize()
                                            ) {
                                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                                            }
                                            is Result.Success -> {
                                                val storyId = backStackEntry.arguments?.getString("storyId")
                                                    ?.toIntOrNull() ?: 0
                                                story.data.data.results.firstOrNull { it.id == storyId }?.let { sectionItem ->
                                                    MarvelSectionImagesScreen(navController = navController, sectionItem = sectionItem)
                                                }
                                            }
                                            is Result.Error -> {
                                                Box(
                                                    modifier = Modifier.fillMaxSize()
                                                ) {
                                                    Text(
                                                        text = "Error: ${story.message}",
                                                        modifier = Modifier.align(Alignment.Center),
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = MaterialTheme.colorScheme.error
                                                    )
                                                }
                                            }
                                            else -> {}
                                        }
                                    }
                                }
//                                composable("story_images/{storyId}") { backStackEntry ->
//                                    val storyId = backStackEntry.arguments?.getString("storyId")?.toIntOrNull() ?: 0
//                                    handleSectionNavigation(storiesState.value, storyId, navController)
//                                }
                            }
                        } else {
                            Text("No characters found.", modifier = Modifier.align(Alignment.Center), style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                    is Result.Error -> {
                        Text(
                            text = "Error: ${charactersResult.message}",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    null -> TODO()
                }
            }
        }
    }
}

//@Composable
//private fun handleSectionNavigation(
//    result: Result<BaseResponse<SectionItem>>?,
//    itemId: Int?,
//    navController: NavHostController
//) {
//    when (result) {
//        is Result.Loading -> Box(
//            modifier = Modifier.fillMaxSize()
//        ) {
//            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//        }
//        is Result.Success -> {
//            result.data.data.results.firstOrNull { it.id == itemId }?.let { sectionItem ->
//                MarvelSectionImagesScreen(navController = navController, sectionItem = sectionItem)
//            }
//        }
//        is Result.Error -> {
//            Box(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                Text(
//                    text = "Error: ${result.message}",
//                    modifier = Modifier.align(Alignment.Center),
//                    style = MaterialTheme.typography.bodyLarge,
//                    color = MaterialTheme.colorScheme.error
//                )
//            }
//        }
//        else -> {}
//    }
//}
