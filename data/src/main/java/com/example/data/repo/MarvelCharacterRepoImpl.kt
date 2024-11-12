package com.example.data.repo

import MarvelSubItem
import com.example.data.remote.ApiService
import com.example.domain.model.BaseResponse
import com.example.domain.model.MarvelCharacter
import com.example.domain.repo.MarvelCharactersRepo

class MarvelCharacterRepoImpl(private val apiService: ApiService):MarvelCharactersRepo {
    override suspend fun getMarvelCharactersFromRemote(): BaseResponse<MarvelCharacter> = apiService.getMarvelCharacters()
    override suspend fun getMarvelSubItemFromRemote(resourceUrl: String): BaseResponse<MarvelSubItem> =apiService.getMarvelSubItemByResourceUrl(resourceUrl)
}