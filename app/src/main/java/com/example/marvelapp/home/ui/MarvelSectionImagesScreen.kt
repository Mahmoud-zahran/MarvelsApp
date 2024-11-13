package com.example.marvelapp.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.domain.model.SectionItem
@Composable
fun MarvelSectionImagesScreen(
    navController: NavController,
    sectionItem: SectionItem
) {
    val images = sectionItem.images ?: emptyList()

    if (images.isEmpty()) {
        // Display a message when there are no images
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No images available",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    } else {
        // HorizontalPager to display images as pages
        val pagerState = rememberPagerState (0,0f,{images.size})

        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier//.fillMaxSize()
            ) { page ->
                Column (Modifier.padding(horizontal = 24.dp)){
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                "${
                                    images[page].path.replace(
                                        "http://",
                                        "https://"
                                    )
                                }.${images[page].extension}"
                            )
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 100.dp,bottom = 16.dp)
                            .aspectRatio(0.75F)
                    )



                Text(
                    text = "${sectionItem.title}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                )
                }
            }

            // Page indicator (e.g., 1/13)
            Text(
                    text = "${pagerState.currentPage + 1}/${images.size}",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp)
                )

            // Close button (X) at the top-right
            IconButton(
                onClick = {
                    navController.popBackStack() // Navigate back when pressed
                },
                modifier = Modifier
                    .padding(top = 40.dp, bottom =  16.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }
    }
}

