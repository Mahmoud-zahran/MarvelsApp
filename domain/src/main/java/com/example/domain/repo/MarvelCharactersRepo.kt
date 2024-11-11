package com.example.domain.repo

import com.example.domain.model.MarvelCharacterResponse

interface MarvelCharactersRepo {
   suspend fun getMarvelCharactersFromRemote():MarvelCharacterResponse
}