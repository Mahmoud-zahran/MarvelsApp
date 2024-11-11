package com.example.marvelapp.di

import com.example.data.remote.ApiService
import com.example.data.repo.MarvelCharacterRepoImpl
import com.example.domain.repo.MarvelCharactersRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    fun provideRepo(apiService: ApiService): MarvelCharactersRepo{
        return MarvelCharacterRepoImpl(apiService)
    }
}