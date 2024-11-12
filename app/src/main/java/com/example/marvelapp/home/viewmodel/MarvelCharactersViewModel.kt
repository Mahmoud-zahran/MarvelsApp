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

const val TAG ="MarvelCharactersViewModel"

@HiltViewModel
class MarvelCharactersViewModel @Inject constructor(
    private val getMarvelCharactersUseCase: GetMarvelCharactersUseCase,
    private val fetchSectionDataUseCase: FetchSectionDataUseCase
): ViewModel() {

    private val _marvelCharacters: MutableStateFlow<BaseResponse<MarvelCharacter>?> = MutableStateFlow(null)
    val marvelCharacters: StateFlow<BaseResponse<MarvelCharacter>?> = _marvelCharacters
    private val _comics = MutableStateFlow<BaseResponse<SectionItem>?>(null)
    val comics: StateFlow<BaseResponse<SectionItem>?> = _comics

    private val _events = MutableStateFlow<BaseResponse<SectionItem>?>(null)
    val events: StateFlow<BaseResponse<SectionItem>?> = _events

    private val _stories = MutableStateFlow<BaseResponse<SectionItem>?>(null)
    val stories: StateFlow<BaseResponse<SectionItem>?> = _stories

    private val _series = MutableStateFlow<BaseResponse<SectionItem>?>(null)
    val series: StateFlow<BaseResponse<SectionItem>?> = _series

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
    fun fetchCharacterDetails(characterId: Int) {
        viewModelScope.launch {
            _comics.value = fetchSectionDataUseCase(characterId,MarvelSectionType.COMIC)
            _events.value = fetchSectionDataUseCase(characterId,MarvelSectionType.EVENT)
            _stories.value = fetchSectionDataUseCase(characterId,MarvelSectionType.STORY)
            _series.value = fetchSectionDataUseCase(characterId,MarvelSectionType.SERIES)
        }
    }

}