package com.example.domain.usecase

import com.example.domain.repo.MarvelCharactersRepo

class GetMarvelCharactersUseCase(private val marvelCharactersRepo: MarvelCharactersRepo) {
suspend operator fun invoke() = marvelCharactersRepo.getMarvelCharactersFromRemote()
}