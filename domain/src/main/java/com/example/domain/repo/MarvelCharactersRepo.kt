package com.example.domain.repo

import MarvelSubItem
import com.example.domain.model.BaseResponse
import com.example.domain.model.MarvelCharacter

interface MarvelCharactersRepo {
   suspend fun getMarvelCharactersFromRemote():BaseResponse<MarvelCharacter>
   suspend fun getMarvelSubItemFromRemote(resourceUrl: String): BaseResponse<MarvelSubItem>
}