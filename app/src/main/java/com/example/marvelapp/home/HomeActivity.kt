package com.example.marvelapp.home

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marvelapp.R
import com.example.marvelapp.ui.theme.MarvelAppTheme
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.lifecycleScope
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.example.domain.model.Result
import com.example.marvelapp.home.viewmodel.MarvelCharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    private val marvelCharacterViewModel: MarvelCharactersViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSizeClass = calculateWindowSizeClass(activity = this)
            MarvelApp(marvelCharacterViewModel, windowSizeClass)
        }
        marvelCharacterViewModel.getMarvelCharacters()

        lifecycleScope.launch {
            marvelCharacterViewModel.marvelCharacters.collect {
                if (it != null) {
                    Log.d("HomeActivity", "onCreate: " + it.data.results.size)
                }

            }

        }
    }


}


// Step: Search bar - Modifiers
@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface
        ),
        placeholder = {
            Text(text = stringResource(R.string.placeholder_search))
        },

        modifier = modifier
            .heightIn(min = 56.dp)
            .fillMaxWidth()
    )
    // Implement composable here
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarvelTopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black), // Set background color to black
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black
        ),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Center Logo
                Spacer(Modifier.size(1.dp))
                Image(
                    painter = painterResource(id = R.drawable.marvel_logo), // Replace with your logo resource
                    contentDescription = "Marvel Logo",
//                    modifier = Modifier.size(30.dp), // Adjust size as needed
                    contentScale = ContentScale.Inside
                )

                // Right Icon
                Icon(
                    imageVector = Icons.Default.Search, // Replace with your search icon resource
                    contentDescription = "Search Icon",
                    tint = Color.Red, // Set the icon color to red
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    )
}


// Step: Align your body - Alignment
@Composable
fun ImageWithTextOverlay(
    marvelCharacter: Result,
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

//       Image(
//            painter = rememberImagePainter(
//                //        .data("${marvelCharacter.thumbnail.path.replace("http://", "https://")}.${marvelCharacter.thumbnail.extension}")
//
//                data = "\"${
//                    marvelCharacter.thumbnail.path.replace(
//                        "http://",
//                        "https://"
//                    )
//                }.${marvelCharacter.thumbnail.extension}"
//            ),
////            painter = painterResource(drawable), // Replace with your image resource
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier.fillMaxSize()
//        )
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


// Step: Align your body row - Arrangements
@Composable
fun ImageWithTextOverlayColum(
     marvelCharacters: List<Result>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {

        items(marvelCharacters) { item ->
            ImageWithTextOverlay(item)
        }
    }
}


// Step: Marvel App - Scaffold
@Composable
fun MarvelAppPortrait(marvelCharacters: List<Result>) {
    // Implement composable here
    MarvelAppTheme {
        Scaffold(
            topBar = { MarvelTopBar() }
        ) { padding ->
            ImageWithTextOverlayColum( marvelCharacters, Modifier.padding(padding))
        }
    }
}


// Step: Marvel App
@Composable
fun MarvelApp(
    viewModel: MarvelCharactersViewModel,
    windowSize: WindowSizeClass
) {

    val marvelCharactersState = viewModel.marvelCharacters.collectAsState(initial = null)

    marvelCharactersState.value?.let { marvelCharacterResponse ->
        if (marvelCharacterResponse.data.results.isNotEmpty()) {
            // Pass the character list to another Composable function for display
//            CharacterList(characters = marvelCharacterResponse.data.results)
            when (windowSize.widthSizeClass) {
                WindowWidthSizeClass.Compact -> {
                    MarvelAppPortrait(marvelCharacterResponse.data.results)
                }

                WindowWidthSizeClass.Expanded -> {
                    MarvelAppPortrait(marvelCharacterResponse.data.results)
                }
            }
        } else {
            Text("No characters found.")
        }
    } ?: run {
        // Display a loading indicator while data is null
//        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterVertically))
    }

}

private val alignYourBodyData = listOf(
    R.drawable.marvel_character to R.string._3_d_man,
    R.drawable.marvel_character to R.string.placeholder_dummy,
    R.drawable.marvel_character to R.string._3_d_man,
    R.drawable.marvel_character to R.string._3_d_man,
    R.drawable.marvel_character to R.string.placeholder_dummy,
    R.drawable.marvel_character to R.string._3_d_man
).map { DrawableStringPair(it.first, it.second) }


private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun SearchBarPreview() {
    MarvelAppTheme { SearchBar(Modifier.padding(8.dp)) }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun TopBarPreview() {
    MarvelAppTheme { MarvelTopBar() }
}


@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun ImageElementPreview() {
    MarvelAppTheme {
//        ImageWithTextOverlay(
//            R.drawable.marvel_character, R.string._3_d_man,
//            modifier = Modifier.padding(8.dp)
//        )
    }
}


@Preview(backgroundColor = 0xFFF5F0EE, showBackground = true)
@Composable
fun AlignYourBodyRowPreview() {
    MarvelAppTheme {
//        ImageWithTextOverlayColum()
    }
}


@Preview(widthDp = 360, heightDp = 640)
@Composable
fun MarvelAppPortraitPreview() {
//    MarvelAppPortrait()
}

