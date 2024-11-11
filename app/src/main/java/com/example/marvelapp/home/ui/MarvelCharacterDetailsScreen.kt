package com.example.marvelapp.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.domain.model.Result
import com.example.marvelapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarvelCharacterDetailsScreen(
    marvelCharacter: Result,
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
        Column( modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 8.dp)) {
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

    }
}

//        // Back Button to navigate back to the list
//        Button(
//            onClick = { navController.popBackStack() },
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        ) {
//            Text(text = "Back to List")
//        }


