package com.example.marvelapp.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.domain.model.MarvelCharacter
import com.example.domain.model.MarvelSectionType
import com.example.domain.model.SectionItem
import com.example.marvelapp.R
import com.example.marvelapp.home.viewmodel.MarvelCharactersViewModel
import com.example.domain.model.Result

@Composable
fun MarvelCharacterDetailsScreen(
    characterId: Int,
    marvelCharacter: MarvelCharacter,
    navController: NavController,
    viewModel: MarvelCharactersViewModel
) {
    val comics = viewModel.comics.collectAsState(initial = null)
    val events = viewModel.events.collectAsState(initial = null)
    val stories = viewModel.stories.collectAsState(initial = null)
    val series = viewModel.series.collectAsState(initial = null)

    LaunchedEffect(characterId) {
        viewModel.fetchCharacterDetails(characterId)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Main content of the screen (image + details)
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // Display character thumbnail (image)
            item {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(
                            "${
                                marvelCharacter.thumbnail.path.replace(
                                    "http://",
                                    "https://"
                                )
                            }.${marvelCharacter.thumbnail.extension}"
                        )
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.FillBounds
                )
            }

            // Display character name and description
            item {
                Column(modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 8.dp)) {
                    Text("NAME", style = MaterialTheme.typography.labelSmall, color = Color.Red)
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        marvelCharacter.name,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("DESCRIPTION", style = MaterialTheme.typography.labelSmall, color = Color.Red)
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        marvelCharacter.description ?: "No description available.", color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // Handle comics result
            comics.value?.let { comicResponse ->
                when (comicResponse) {
                    is Result.Loading -> {
                        item { LoadingItem() }
                    }
                    is Result.Success -> {
                        val comicsData = comicResponse.data.data.results
                        if (comicsData.isNotEmpty()) {
                            item {
                                SubCollectionSection(
                                    title = "COMICS", characterId, navController, MarvelSectionType.COMIC, items = comicsData
                                )
                            }
                        }
                    }
                    is Result.Error -> {
                        item {
                            ErrorItem(message = comicResponse.message)
                        }
                    }
                }
            }

            // Handle series result
            series.value?.let { seriesResponse ->
                when (seriesResponse) {
                    is Result.Loading -> {
                        item { LoadingItem() }
                    }
                    is Result.Success -> {
                        val seriesData = seriesResponse.data.data.results
                        if (seriesData.isNotEmpty()) {
                            item {
                                SubCollectionSection(
                                    title = "SERIES", characterId, navController, MarvelSectionType.SERIES, items = seriesData
                                )
                            }
                        }
                    }
                    is Result.Error -> {
                        item {
                            ErrorItem(message = seriesResponse.message)
                        }
                    }
                }
            }

            // Handle stories result
            stories.value?.let { storiesResponse ->
                when (storiesResponse) {
                    is Result.Loading -> {
                        item { LoadingItem() }
                    }
                    is Result.Success -> {
                        val storiesData = storiesResponse.data.data.results
                        if (storiesData.isNotEmpty()) {
                            item {
                                SubCollectionSection(
                                    title = "STORIES", characterId, navController, MarvelSectionType.STORY, items = storiesData
                                )
                            }
                        }
                    }
                    is Result.Error -> {
                        item {
                            ErrorItem(message = storiesResponse.message)
                        }
                    }
                }
            }

            // Handle events result
            events.value?.let { eventsResponse ->
                when (eventsResponse) {
                    is Result.Loading -> {
                        item { LoadingItem() }
                    }
                    is Result.Success -> {
                        val eventsData = eventsResponse.data.data.results
                        if (eventsData.isNotEmpty()) {
                            item {
                                SubCollectionSection(
                                    title = "EVENTS", characterId, navController, MarvelSectionType.EVENT, items = eventsData
                                )
                            }
                        }
                    }
                    is Result.Error -> {
                        item {
                            ErrorItem(message = eventsResponse.message)
                        }
                    }
                }
            }

            // Related links section
            item {
                Column(modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 8.dp)) {
                    Text("RELATED LINKS", style = MaterialTheme.typography.labelSmall, color = Color.Red)
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Details", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Arrow Right")
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Wiki", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Arrow Right")
                    }
                    Spacer(modifier = Modifier.height(4.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Comiclink", style = MaterialTheme.typography.bodyLarge, color = Color.White)
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Arrow Right")
                    }

                    Spacer(modifier = Modifier.height(100.dp))
                }
            }

        }

        // Back arrow placed on top-left of the image (stacked over the image)
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back")
        }
    }
}

@Composable
fun LoadingItem() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun ErrorItem(message: String?) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = message ?: "An error occurred.",
            color = Color.Red,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Composable
fun SubCollectionSection(
    title: String,
    characterId: Int,
    navController: NavController,
    marvelSectionType: MarvelSectionType,
    items: List<SectionItem> // Make MarvelItem an interface with properties like name and thumbnail
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = title, style = MaterialTheme.typography.labelSmall, color = Color.Red)

        LazyRow {
            items(items) { item ->
                if (item.thumbnail != null) {
                    SubCollectionCardItem(item  ,    characterId,
                        marvelSectionType  ,navController)
                }
            }
        }
    }
}

@Composable
fun SubCollectionCardItem(item: SectionItem ,    characterId: Int,
                          marvelSectionType: MarvelSectionType,navController: NavController
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,

        modifier = Modifier
            .width(120.dp)
            .padding(bottom = 8.dp, start = 4.dp)
            .clickable {
                when (marvelSectionType) {
                    MarvelSectionType.COMIC -> navController.navigate("comic_images/${item.id}")
                    { popUpTo("character_details/${characterId}") { inclusive = false }}
                    MarvelSectionType.EVENT -> navController.navigate("event_images/${item.id}")
                    { popUpTo("character_details/${characterId}") { inclusive = false }}
                    MarvelSectionType.SERIES ->navController.navigate("series_images/${item.id}")
                    { popUpTo("character_details/${characterId}") { inclusive = false }}
                    MarvelSectionType.STORY -> navController.navigate("story_images/${item.id}")
                    { popUpTo("character_details/${characterId}") { inclusive = false }}
//            else -> marvelCharactersRepo.getComics(characterId) // Default case
                }
            }
    ) {
        AsyncImage(
            model = item.thumbnail.path.let {
                "${it.replace("http://", "https://")}.${item.thumbnail.extension}"
            } ?: R.drawable.marvel_logo,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            placeholder = painterResource(R.drawable.marvel_character),
            error = painterResource(R.drawable.marvel_logo),
            modifier = Modifier
                .aspectRatio(0.75f)
                .clip(RoundedCornerShape(2.dp))
        )
        Text(
            text = "${item.title}",
            color = Color.White,
            style = MaterialTheme.typography.labelSmall,
//            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 4.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

    }
}
