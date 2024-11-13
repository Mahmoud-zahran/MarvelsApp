package com.example.marvelapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.BaseResponse
import com.example.domain.model.MarvelCharacter
import com.example.domain.model.MarvelSectionType
import com.example.domain.model.SectionItem
import com.example.domain.usecase.FetchSectionDataUseCase
import com.example.domain.usecase.GetMarvelCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.domain.model.Result

const val TAG ="MarvelCharactersViewModel"

@HiltViewModel
class MarvelCharactersViewModel @Inject constructor(
    private val getMarvelCharactersUseCase: GetMarvelCharactersUseCase,
    private val fetchSectionDataUseCase: FetchSectionDataUseCase
): ViewModel() {

    private val _marvelCharacters: MutableStateFlow<Result<BaseResponse<MarvelCharacter>>?> = MutableStateFlow(null)
    val marvelCharacters: StateFlow<Result<BaseResponse<MarvelCharacter>>?> = _marvelCharacters
    private val _comics = MutableStateFlow<Result<BaseResponse<SectionItem>>?>(null)
    val comics: StateFlow<Result<BaseResponse<SectionItem>>?> = _comics

    private val _events = MutableStateFlow<Result<BaseResponse<SectionItem>>?>(null)
    val events: StateFlow<Result<BaseResponse<SectionItem>>?> = _events

    private val _stories = MutableStateFlow<Result<BaseResponse<SectionItem>>?>(null)
    val stories: StateFlow<Result<BaseResponse<SectionItem>>?> = _stories

    private val _series = MutableStateFlow<Result<BaseResponse<SectionItem>>?>(null)
    val series: StateFlow<Result<BaseResponse<SectionItem>>?> = _series

    fun getMarvelCharacters(){
        viewModelScope.launch {
            _marvelCharacters.value = Result.Loading  // Set loading state
            try {
                val result = getMarvelCharactersUseCase()
                _marvelCharacters.value = result
                Log.d(TAG, "getMarvelCharacters: $result")
            } catch (e: Exception) {
                _marvelCharacters.value = Result.Error("Failed to fetch characters", e)
                Log.e(TAG, "getMarvelCharacters Error: ${e.message}")
            }
        }
    }

    fun fetchCharacterDetails(characterId: Int) {
        viewModelScope.launch {
            // Fetch comics
            _comics.value = Result.Loading
            _comics.value = try {
                fetchSectionDataUseCase(characterId, MarvelSectionType.COMIC)
            } catch (e: Exception) {
                Result.Error("Failed to fetch comics", e)
            }

            // Fetch events
            _events.value = Result.Loading
            _events.value = try {
                fetchSectionDataUseCase(characterId, MarvelSectionType.EVENT)
            } catch (e: Exception) {
                Result.Error("Failed to fetch events", e)
            }

            // Fetch stories
            _stories.value = Result.Loading
            _stories.value = try {
                fetchSectionDataUseCase(characterId, MarvelSectionType.STORY)
            } catch (e: Exception) {
                Result.Error("Failed to fetch stories", e)
            }

            // Fetch series
            _series.value = Result.Loading
            _series.value = try {
                fetchSectionDataUseCase(characterId, MarvelSectionType.SERIES)
            } catch (e: Exception) {
                Result.Error("Failed to fetch series", e)
            }
        }
    }
//

}