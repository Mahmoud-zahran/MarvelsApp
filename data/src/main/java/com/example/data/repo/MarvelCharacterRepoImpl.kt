package com.example.data.repo

import com.example.data.remote.ApiService
import com.example.domain.model.BaseResponse
import com.example.domain.model.MarvelCharacter
import com.example.domain.model.SectionItem
import com.example.domain.repo.MarvelCharactersRepo

class MarvelCharacterRepoImpl(private val apiService: ApiService):MarvelCharactersRepo {
    override suspend fun getMarvelCharactersFromRemote(): BaseResponse<MarvelCharacter> = apiService.getMarvelCharacters()
    override suspend fun getComics(characterId: Int): BaseResponse<SectionItem> = apiService.getCharacterComics(characterId)
    override suspend fun getEvents(characterId: Int): BaseResponse<SectionItem> = apiService.getCharacterEvents(characterId)
    override suspend fun getStories(characterId: Int): BaseResponse<SectionItem> = apiService.getCharacterStories(characterId)
    override suspend fun getSeries(characterId: Int): BaseResponse<SectionItem> = apiService.getCharacterSeries(characterId)
}