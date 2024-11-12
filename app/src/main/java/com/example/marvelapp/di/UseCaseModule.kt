package com.example.marvelapp.di

import com.example.domain.repo.MarvelCharactersRepo
import com.example.domain.usecase.GetMarvelCharactersUseCase
import com.example.domain.usecase.GetMarvelSubItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideMarvelCharactersUseCase(marvelCharactersRepo: MarvelCharactersRepo): GetMarvelCharactersUseCase{
        return GetMarvelCharactersUseCase(marvelCharactersRepo)
    }
    @Provides
    fun provideMarvelSubItemUseCase(marvelCharactersRepo: MarvelCharactersRepo): GetMarvelSubItemUseCase{
        return GetMarvelSubItemUseCase(marvelCharactersRepo)
    }
}