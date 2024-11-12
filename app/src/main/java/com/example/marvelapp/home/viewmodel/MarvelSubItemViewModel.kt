package com.example.marvelapp.home.viewmodel

import MarvelSubItem
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.BaseResponse
import com.example.domain.model.MarvelCharacter
import com.example.domain.usecase.GetMarvelCharactersUseCase
import com.example.domain.usecase.GetMarvelSubItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarvelSubItemViewModel @Inject constructor(
    private val getMarvelSubItemUseCase: GetMarvelSubItemUseCase
): ViewModel() {

    private val _marvelCharacters: MutableStateFlow<BaseResponse<MarvelSubItem>?> = MutableStateFlow(null)
    val marvelCharacters: StateFlow<BaseResponse<MarvelSubItem>?> = _marvelCharacters

    fun getMarvelSubItem(resourceUrl:String){
        viewModelScope.launch{
            try {
                _marvelCharacters.value = getMarvelSubItemUseCase(resourceUrl)
                Log.d(TAG, "getMarvelCharacters: "+_marvelCharacters.value.toString())
            } catch (e: Exception){
                Log.e(TAG, e.message.toString())
            }
        }
    }

}