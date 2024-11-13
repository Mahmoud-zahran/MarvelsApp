package com.example.data.repo

import com.example.data.remote.ApiService
import com.example.domain.model.BaseResponse
import com.example.domain.model.MarvelCharacter
import com.example.domain.model.SectionItem
import com.example.domain.repo.MarvelCharactersRepo
import com.example.domain.model.Result

class MarvelCharacterRepoImpl(private val apiService: ApiService):MarvelCharactersRepo {
    override suspend fun getMarvelCharactersFromRemote(): Result<BaseResponse<MarvelCharacter>> {
        return try {
            val response = apiService.getMarvelCharacters()
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception: ${e.localizedMessage}", e)
        }
    }
    override suspend fun getComics(characterId: Int): Result<BaseResponse<SectionItem>> {
        return try {
            val response = apiService.getCharacterComics(characterId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception: ${e.localizedMessage}", e)
        }
    }
    override suspend fun getEvents(characterId: Int): Result<BaseResponse<SectionItem>> {
        return try {
            val response = apiService.getCharacterEvents(characterId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception: ${e.localizedMessage}", e)
        }
    }
    override suspend fun getStories(characterId: Int): Result<BaseResponse<SectionItem>> {
        return try {
            val response = apiService.getCharacterStories(characterId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception: ${e.localizedMessage}", e)
        }
    }
    override suspend fun getSeries(characterId: Int): Result<BaseResponse<SectionItem>> {
        return try {
            val response = apiService.getCharacterSeries(characterId)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error("Error: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception: ${e.localizedMessage}", e)
        }
    }
}