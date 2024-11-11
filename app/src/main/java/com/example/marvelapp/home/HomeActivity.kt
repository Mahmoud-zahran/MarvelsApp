package com.example.marvelapp.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.marvelapp.home.ui.MarvelAppScreen
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
            val navController = rememberNavController()
            MarvelAppScreen(
                viewModel = marvelCharacterViewModel,
                windowSize = calculateWindowSizeClass(activity = this),
                navController = navController
            )
        }

        lifecycleScope.launch {
            marvelCharacterViewModel.getMarvelCharacters()
        }
    }
}


