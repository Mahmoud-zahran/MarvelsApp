package com.example.marvelapp.home.ui

import MarvelSubItem
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.domain.model.Item
import com.example.domain.model.MarvelCharacter
import com.example.marvelapp.R
import com.example.marvelapp.home.viewmodel.MarvelSubItemViewModel
import com.example.marvelapp.ui.theme.MarvelAppTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarvelCharacterDetailsScreen(
    marvelSubItemViewModel: MarvelSubItemViewModel,
    marvelCharacter: MarvelCharacter,
   imageLoader: ImageLoader,
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
//            .padding(16.dp)
    ) {
        // Display character's image
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
            placeholder = painterResource(R.drawable.marvel_character),
            error = painterResource(R.drawable.marvel_logo),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxWidth()/*.aspectRatio(1.5F)*/
        )

        // Display character's name and label
        Column(modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 8.dp))
        {
            Text(
                text = "Name:",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = marvelCharacter.name,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Display character's description label and value
            Text(
                text = "Description:",
                color = Color.Red,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = marvelCharacter.description.ifEmpty { "No description available" },
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }


                // Comics Section
                if (marvelCharacter.comics.available > 0) {
                    SubCollectionSection(
                        marvelSubItemViewModel,
                        "Comics",
                        marvelCharacter.comics.items,
                        imageLoader = imageLoader
                    )
                }

                // Series Section
                if (marvelCharacter.series.available > 0) {

                    SubCollectionSection(
                           marvelSubItemViewModel,
                        "Series",
                        marvelCharacter.series.items,
                        imageLoader = imageLoader
                    )
                }

                // Stories Section
                if (marvelCharacter.stories.available > 0) {
                    SubCollectionSection(
                        marvelSubItemViewModel,
                        "Stories",
                        marvelCharacter.stories.items,
                        imageLoader = imageLoader
                    )
                }

                // Events Section
                if (marvelCharacter.events.available > 0) {
                    SubCollectionSection(
                        marvelSubItemViewModel,
                        "Events",
                        marvelCharacter.events.items,
                        imageLoader = imageLoader
                    )
                }
            }

        }



    //SubCollection Section with Title
    @Composable
    fun SubCollectionSection(
        marvelSubItemViewModel: MarvelSubItemViewModel,
        title: String,
        items: List<Item>,
        imageLoader: ImageLoader,
        modifier: Modifier = Modifier
    ) {

            Column(modifier = modifier.padding(top = 16.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyRow {
                    items(items) { item ->
                        LaunchedEffect(Unit) {
                            marvelSubItemViewModel.getMarvelSubItem(item.resourceURI.toString())
                        }
                        SubCollectionCardItem(marvelSubItemViewModel,
                            imageLoader = imageLoader,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    }
                }
            }
                }



    // Step: SubCollection Card Item - Material Surface
    @Composable
    fun SubCollectionCardItem(
        marvelSubItemViewModel:MarvelSubItemViewModel,
//        marvelSubItem:MarvelSubItem,
        imageLoader: ImageLoader, // Injected ImageLoader
        modifier: Modifier = Modifier
    ) {
        val marvelSubItemState =
            marvelSubItemViewModel.marvelCharacters.collectAsState(initial = null)
        // Implement composable here
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = modifier
        ) {
            marvelSubItemState.value?.let { marvelCharacterResponse ->
                if (marvelCharacterResponse.data.results.isNotEmpty()) {
                    val marvelSubItem = marvelCharacterResponse.data.results
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()/*width(255.dp)*/
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
//                .data("https://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16.jpg")
                                .data(
                                    "${
                                        marvelSubItem.get(0).thumbnail.path.replace(
                                            "http://",
                                            "https://"
                                        )
                                    }.${marvelSubItem.get(0).thumbnail.extension}"
                                )
                                .crossfade(true)
                                .build(),
//                    imageLoader = imageLoader,
                            placeholder = painterResource(R.drawable.marvel_character),
                            error = painterResource(R.drawable.marvel_logo),
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
//                    .size(80.dp),
//                    .heightIn(min = 80.dp)
//                    .widthIn(min = 255.dp)
                                .clip(RectangleShape)/*.aspectRatio(1.5F)*/
                        )

                        marvelSubItem.get(0).title?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }


//@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
//@Composable
//fun FavoriteCollectionCardPreview() {
//    MarvelAppTheme {
//        SubCollectionCard(
//           marvelCharacter = MarvelCharacter()
////            ,
////            modifier = Modifier.padding(8.dp)
//        )
//    }
//}
//        // Back Button to navigate back to the list
//        Button(
//            onClick = { navController.popBackStack() },
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        ) {
//            Text(text = "Back to List")
//        }


