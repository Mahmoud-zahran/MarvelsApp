package com.example.marvelapp.home.ui

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.domain.model.MarvelCharacter
import com.example.marvelapp.R
import com.example.marvelapp.ui.theme.MarvelAppTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarvelCharacterDetailsScreen(
    marvelCharacter: MarvelCharacter,
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


    }
}
//SubCollection List with label
@Composable
fun SubCollectionList(){

}

// Step: SubCollection Card Item - Material Surface
@Composable
fun SubCollectionCardItem(
    marvelCharacter: MarvelCharacter,
    modifier: Modifier = Modifier
) {
    // Implement composable here
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()/*width(255.dp)*/
        ) {
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
                modifier = Modifier
//                    .size(80.dp),
//                    .heightIn(min = 80.dp)
//                    .widthIn(min = 255.dp)
                    .clip(RectangleShape)/*.aspectRatio(1.5F)*/
            )

            Text(
                text = marvelCharacter.name,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
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


