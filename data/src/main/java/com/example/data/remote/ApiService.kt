package com.example.data.remote

import com.example.domain.model.BaseResponse
import com.example.domain.model.MarvelCharacter
import com.example.domain.model.SectionItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
//    get /v1/public/characters
@GET("characters")
suspend fun getMarvelCharacters(): BaseResponse<MarvelCharacter>

    @GET("characters/{characterId}/comics")
    suspend fun getCharacterComics(
        @Path("characterId") characterId: Int
    ): BaseResponse<SectionItem>

    @GET("characters/{characterId}/events")
    suspend fun getCharacterEvents(
        @Path("characterId") characterId: Int
    ): BaseResponse<SectionItem>

    @GET("characters/{characterId}/stories")
    suspend fun getCharacterStories(
        @Path("characterId") characterId: Int
    ): BaseResponse<SectionItem>

    @GET("characters/{characterId}/series")
    suspend fun getCharacterSeries(
        @Path("characterId") characterId: Int
    ): BaseResponse<SectionItem>
}
