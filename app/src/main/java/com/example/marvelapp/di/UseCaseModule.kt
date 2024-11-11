package com.example.marvelapp.di

import com.example.domain.repo.MarvelCharactersRepo
import com.example.domain.usecase.GetMarvelCharactersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideUseCase(marvelCharactersRepo: MarvelCharactersRepo): GetMarvelCharactersUseCase{
        return GetMarvelCharactersUseCase(marvelCharactersRepo)
    }
}