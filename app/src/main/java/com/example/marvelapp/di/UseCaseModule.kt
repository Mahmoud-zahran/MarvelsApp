package com.example.marvelapp.di

import com.example.domain.repo.MarvelCharactersRepo
import com.example.domain.usecase.FetchSectionDataUseCase
import com.example.domain.usecase.GetMarvelCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideMarvelCharacterUseCase(marvelCharactersRepo: MarvelCharactersRepo): GetMarvelCharactersUseCase{
        return GetMarvelCharactersUseCase(marvelCharactersRepo)
    }
    @Provides
    fun provideMarvelSectionDataUseCase(marvelCharactersRepo: MarvelCharactersRepo): FetchSectionDataUseCase{
        return FetchSectionDataUseCase(marvelCharactersRepo)
    }
}