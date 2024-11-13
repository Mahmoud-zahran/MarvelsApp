package com.example.domain.repo

import com.example.domain.model.BaseResponse
import com.example.domain.model.MarvelCharacter
import com.example.domain.model.SectionItem
import com.example.domain.model.Result

interface MarvelCharactersRepo {
   suspend fun getMarvelCharactersFromRemote():Result<BaseResponse<MarvelCharacter>>
   suspend fun getComics(characterId: Int): Result<BaseResponse<SectionItem>>
   suspend fun getEvents(characterId: Int): Result<BaseResponse<SectionItem>>
   suspend fun getStories(characterId: Int): Result<BaseResponse<SectionItem>>
   suspend fun getSeries(characterId: Int): Result<BaseResponse<SectionItem>>
}