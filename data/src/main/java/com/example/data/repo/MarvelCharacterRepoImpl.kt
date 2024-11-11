package com.example.data.repo

import com.example.data.remote.ApiService
import com.example.domain.model.MarvelCharacterResponse
import com.example.domain.repo.MarvelCharactersRepo

class MarvelCharacterRepoImpl(private val apiService: ApiService):MarvelCharactersRepo {
    override suspend fun getMarvelCharactersFromRemote(): MarvelCharacterResponse = apiService.getMarvelCharacters()
}