package com.example.trendhub.data.repo

import com.example.trendhub.data.local.db.AppDbHelper
import com.example.trendhub.data.local.db.model.RoomRepo
import com.example.trendhub.data.model.Repo
import com.example.trendhub.data.services.CoroutineApiService
import com.example.trendhub.data.services.NetworkResult
import io.reactivex.Completable
import io.reactivex.Flowable

class GithubRepo constructor(
    private val coroutineApiService: CoroutineApiService,
    private val appDbHelper: AppDbHelper
) {

    suspend fun getMostTrendingReposCoroutine(
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

    fun insertAllReposInDatabase(repositories: List<RoomRepo>): Completable {
        return appDbHelper.saveAllRepositories(repositories)
    }

    fun getMostTrendingReposFromDatabase(): Flowable<List<RoomRepo>> {
        val response = appDbHelper.getMostTrendingRepositories()
        return response
    }
}