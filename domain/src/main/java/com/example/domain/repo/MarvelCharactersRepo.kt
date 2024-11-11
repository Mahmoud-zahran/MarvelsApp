package com.example.domain.repo

import com.example.domain.model.BaseResponse
import com.example.domain.model.MarvelCharacter

interface MarvelCharactersRepo {
   suspend fun getMarvelCharactersFromRemote():BaseResponse<MarvelCharacter>
}