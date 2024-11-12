package com.example.domain.repo

import com.example.domain.model.BaseResponse
import com.example.domain.model.MarvelCharacter
import com.example.domain.model.SectionItem

interface MarvelCharactersRepo {
   suspend fun getMarvelCharactersFromRemote():BaseResponse<MarvelCharacter>
   suspend fun getComics(characterId: Int): BaseResponse<SectionItem>
   suspend fun getEvents(characterId: Int): BaseResponse<SectionItem>
   suspend fun getStories(characterId: Int): BaseResponse<SectionItem>
   suspend fun getSeries(characterId: Int): BaseResponse<SectionItem>
}