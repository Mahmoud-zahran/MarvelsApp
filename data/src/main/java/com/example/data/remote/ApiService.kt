package com.example.data.remote

import com.example.domain.model.MarvelCharacterResponse
import retrofit2.http.GET

interface ApiService {
//    get /v1/public/characters
@GET("characters")
suspend fun getMarvelCharacters(): MarvelCharacterResponse
}
