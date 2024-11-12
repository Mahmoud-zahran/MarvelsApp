package com.example.domain.usecase

import com.example.domain.repo.MarvelCharactersRepo

class GetMarvelSubItemUseCase(private val marvelCharactersRepo: MarvelCharactersRepo) {
    suspend operator fun invoke(resourceUrl: String) =
        marvelCharactersRepo.getMarvelSubItemFromRemote(resourceUrl)
}