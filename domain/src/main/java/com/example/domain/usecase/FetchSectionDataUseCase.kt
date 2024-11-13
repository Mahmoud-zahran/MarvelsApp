package com.example.domain.usecase

import com.example.domain.model.BaseResponse
import com.example.domain.model.MarvelSectionType
import com.example.domain.model.SectionItem
import com.example.domain.repo.MarvelCharactersRepo
import com.example.domain.model.Result

class FetchSectionDataUseCase (private val marvelCharactersRepo: MarvelCharactersRepo) {
    suspend operator fun invoke(characterId: Int, type: MarvelSectionType): Result<BaseResponse<SectionItem>> {
        return when (type) {
            MarvelSectionType.COMIC -> marvelCharactersRepo.getComics(characterId)
            MarvelSectionType.EVENT -> marvelCharactersRepo.getEvents(characterId)
            MarvelSectionType.SERIES -> marvelCharactersRepo.getSeries(characterId)
            MarvelSectionType.STORY -> marvelCharactersRepo.getStories(characterId)
//            else -> marvelCharactersRepo.getComics(characterId) // Default case
        }
    }
}