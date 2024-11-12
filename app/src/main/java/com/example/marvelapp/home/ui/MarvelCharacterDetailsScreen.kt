package com.example.marvelapp.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.example.domain.model.SectionItem
import com.example.marvelapp.R
import com.example.marvelapp.home.viewmodel.MarvelCharactersViewModel

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
            modifier = Modifier
                .fillMaxSize()
//                .padding(top = 56.dp) // Padding to avoid overlap with back arrow
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
//                        .aspectRatio(16 / 9f) // Use aspect ratio to keep height dynamic
//                        .fillMaxWidth()
                        .height(300.dp), // Set height to make the image go to the edge
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

            // Display sections (Comics, Events, etc.)
            comics.value?.let { comicResponse ->
                if (comicResponse.data.results.isNotEmpty()) {
                    @Suppress("NAME_SHADOWING") val comics = comicResponse.data.results
                    item {
                        SubCollectionSection(title = "COMICS", items = comics)
                    }
                }
            }
            series.value?.let { seriesResponse ->
                if (seriesResponse.data.results.isNotEmpty()) {
                    @Suppress("NAME_SHADOWING") val series = seriesResponse.data.results
                    item {
                        SubCollectionSection(title = "Series", items = series)
                    }
                }
            }
            stories.value?.let { storiesResponse ->
                if (storiesResponse.data.results.isNotEmpty()) {
                    @Suppress("NAME_SHADOWING") val stories = storiesResponse.data.results
                    item {
                        SubCollectionSection(title = "Stories", items = stories)
                    }
                }
            }
            events.value?.let { eventsResponse ->
                if (eventsResponse.data.results.isNotEmpty()) {
                    @Suppress("NAME_SHADOWING") val events = eventsResponse.data.results
                    item {
                        SubCollectionSection(title = "Events", items = events)
                    }
                }
            }
        }

        // Back arrow placed on top-left of the image (stacked over the image)
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp) // Adjust padding for correct placement
                .align(Alignment.TopStart) // Align the back arrow to the top start (top-left)
        ) {
            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back")
        }
    }
}

@Composable
fun SubCollectionSection(
    title: String,
    items: List<SectionItem> // Make MarvelItem an interface with properties like name and thumbnail
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = title, style = MaterialTheme.typography.labelSmall, color = Color.Red)

        LazyRow {
            items(items) { item ->
                if (item.thumbnail != null) {
                    SubCollectionCardItem(item)
                }
            }
        }
    }
}

@Composable
fun SubCollectionCardItem(item: SectionItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(120.dp)
            .padding(bottom = 8.dp, start = 4.dp)
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
