package com.example.trendhub.data.repo

import com.example.trendhub.data.model.Repo
import com.example.trendhub.data.services.CoroutineApiService
import com.example.trendhub.data.services.NetworkResult

class GithubRepo constructor(private val coroutineApiService: CoroutineApiService) {

    suspend fun getMostTrendingRepositories(
        language: String,
        since: String,
        spokenLangCode: String
    ): NetworkResult<List<Repo>> {
        val response =
            coroutineApiService.getTrendingRepositories(language, since, spokenLangCode)
        if (response.isSuccessful) {
            val data = response.body()
            if (data != null) {
                return NetworkResult.Success(data)
            }
        }
        return NetworkResult.Failure(response)
    }
}