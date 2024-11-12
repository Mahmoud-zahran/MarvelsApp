package com.example.marvelapp.home.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.domain.model.Item
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
//    val events = viewModel.events.collectAsState(initial = null)
//    val stories = viewModel.stories.collectAsState(initial = null)
//    val series = viewModel.series.collectAsState(initial = null)


    LaunchedEffect(characterId) {
        viewModel.fetchCharacterDetails(characterId)
    }


    LazyColumn(
        modifier = Modifier.fillMaxSize(),

    ) {
        // Display character thumbnail
        item {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
//                .data("https://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg")
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
                modifier = Modifier
                    .fillMaxWidth()
                    ,
                contentScale = ContentScale.FillBounds
            )
        }

        // Display character name and description
        item {
            Column(modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 8.dp)){

                Text("NAME", style = MaterialTheme.typography.labelSmall, color = Color.Red)
                Spacer(modifier = Modifier.height(4.dp))

                Text(marvelCharacter.name,  color = Color.White, style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(8.dp))

                Text("DESCRIPTION", style = MaterialTheme.typography.labelSmall, color = Color.Red)
                Spacer(modifier = Modifier.height(4.dp))

                Text(marvelCharacter.description ?: "No description available.",  color = Color.White,
                    style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(8.dp))

            }
        }
        comics.value?.let { comicResponse ->
            if (comicResponse.data.results.isNotEmpty()) {
                @Suppress("NAME_SHADOWING") val comics = comicResponse.data.results
                item {
                    SubCollectionSection(title = "COMICS", items = comics)
                }
            }
        }

        // Display events section if data is available
        /*
                if (events.isNotEmpty()) {
                    item {
                        Text("EVENTS", style = MaterialTheme.typography.subtitle2, color = Color.Red)
                    }
                    item {
                        LazyRow {
                            items(events) { event ->
                                AsyncImage(
                                    model = event.imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(end = 8.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
        */

        // Display stories section if data is available
        /*
                if (stories.isNotEmpty()) {
                    item {
                        Text("STORIES", style = MaterialTheme.typography.subtitle2, color = Color.Red)
                    }
                    item {
                        LazyRow {
                            items(stories) { story ->
                                AsyncImage(
                                    model = story.imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(end = 8.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
        */

        // Display series section if data is available
        /*
                if (series.isNotEmpty()) {
                    item {
                        Text("SERIES", style = MaterialTheme.typography.labelMedium, color = Color.Red)
                    }
                    item {
                        LazyRow {
                            items(series) { seriesItem ->
                                AsyncImage(
                                    model = seriesItem.imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .padding(end = 8.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
        */
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
                SubCollectionCardItem(item)
            }
        }
    }
}

@Composable
fun SubCollectionCardItem(item: SectionItem) {
    Column (horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
        .width(120.dp)
        .padding(bottom = 8.dp, start = 4.dp)){
        AsyncImage(
            model = "${
                item.thumbnail.path.replace(
                    "http://",
                    "https://"
                )
            }.${item.thumbnail.extension}",
            contentScale = ContentScale.Crop,
            contentDescription = null,
            placeholder = painterResource(R.drawable.marvel_character),
            error = painterResource(R.drawable.marvel_logo),
            modifier = Modifier
                .aspectRatio(0.75f)
                .clip(RoundedCornerShape(2.dp))
        )
        Text(text = "${item.title}",
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