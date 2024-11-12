package com.example.data.remote

import MarvelSubItem
import com.example.domain.model.BaseResponse
import com.example.domain.model.MarvelCharacter
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {
  //    get /v1/public/characters
  @GET("characters")
  suspend fun getMarvelCharacters(): BaseResponse<MarvelCharacter>

  // Define the endpoint for getting comics by character ID
  @GET
  suspend fun getMarvelSubItemByResourceUrl(@Url resourceUrl: String
    ): BaseResponse<MarvelSubItem>
}
