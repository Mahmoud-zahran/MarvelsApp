package com.example.marvelapp.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.domain.model.MarvelCharacter
import com.example.marvelapp.R

@Composable
fun MarvelCharacterListScreen(
    marvelCharacters: List<MarvelCharacter>,
    navController: NavController,
    loadNextPage: () -> Unit, // Function to load the next page
    isLoading: Boolean, // Whether more characters are being loaded
    modifier: Modifier = Modifier
) {
    // Track the list scroll state
    val listState = rememberLazyListState()
    // Detect when user has scrolled to the end
    LaunchedEffect(listState.firstVisibleItemIndex) {
        if (!isLoading && listState.isScrolledToEnd()) {
            loadNextPage()
        }
    }
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxWidth().padding(top = 80.dp, bottom = 40.dp)
    ) {
        items(marvelCharacters) { character ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
//                    .padding(8.dp)
                    .clickable {
                        navController.navigate("character_details/${character.id}")
                    }
            ) {
                ImageWithTextOverlay(character)
            }
        } // Show a loading indicator when new characters are being loaded
        if (isLoading) {
            item {
                Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}
// Extension function to check if the list is scrolled to the end
fun LazyListState.isScrolledToEnd(): Boolean {
    return layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
}
@Composable
fun ImageWithTextOverlay(
    marvelCharacter: MarvelCharacter,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2.5f) // Adjust the aspect ratio based on the image dimensions
    ) {
        // Background Image
        // Character thumbnail using AsyncImage
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
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .background(
                    color = Color.White,
                    shape = ParallelogramShape
                )
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Text(
                text = marvelCharacter.name, // Replace with your text
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

// Custom Parallelogram Shape
val ParallelogramShape = GenericShape { size, _ ->
    moveTo(20f, 0f)              // Start point of the top left, move it to the right to create skew
    lineTo(size.width, 0f)       // Top right corner
    lineTo(size.width - 20f, size.height) // Bottom right corner, moved left to create skew
    lineTo(0f, size.height)      // Bottom left corner
    close()                      // Close the shape
}
