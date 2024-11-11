package com.example.marvelapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MarvelCharacterResponse
import com.example.domain.usecase.GetMarvelCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG ="MarvelCharactersViewModel"

@HiltViewModel
class MarvelCharactersViewModel @Inject constructor(
    private val getMarvelCharactersUseCase: GetMarvelCharactersUseCase
): ViewModel() {

    private val _marvelCharacters: MutableStateFlow<MarvelCharacterResponse?> = MutableStateFlow(null)
    val marvelCharacters: StateFlow<MarvelCharacterResponse?> = _marvelCharacters

    fun getMarvelCharacters(){
        viewModelScope.launch{
            try {
                _marvelCharacters.value = getMarvelCharactersUseCase()
                Log.d(TAG, "getMarvelCharacters: "+_marvelCharacters.value.toString())
            } catch (e: Exception){
                Log.e(TAG, e.message.toString())
            }
        }
    }

}