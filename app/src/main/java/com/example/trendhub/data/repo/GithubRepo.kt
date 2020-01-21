package com.example.trendhub.data.repo

import androidx.lifecycle.LiveData
import com.example.trendhub.data.local.db.AppDatabase
import com.example.trendhub.data.local.db.DbHelper
import com.example.trendhub.data.local.db.model.RoomRepo
import com.example.trendhub.data.model.Repo
import com.example.trendhub.data.services.ApiHelper
import com.example.trendhub.data.services.CoroutineApiService
import com.example.trendhub.data.services.NetworkResult
import io.reactivex.Completable
import io.reactivex.Flowable

class GithubRepo constructor(
    private val apiService: CoroutineApiService, private val appDatabase: AppDatabase
) : DbHelper, ApiHelper {

    override suspend fun getReposFromApi(
        language: String,
        since: String,
        spokenLangCode: String
    ): NetworkResult<List<Repo>> {
        val response = apiService.getTrendingReposFromApi(language, since, spokenLangCode)
        if (response.isSuccessful) {
            val data = response.body()
            if (data != null) {
                return NetworkResult.Success(data)
            }
        }
        return NetworkResult.Failure(response)
    }

    override fun saveRepoInDb(repository: RoomRepo) {
        appDatabase.repoDao().saveRepo(repository)
    }

    override fun saveReposInDb(repositories: List<RoomRepo>): Completable {
        return appDatabase.repoDao().saveRepos(*repositories.toTypedArray())
    }

    override fun getReposFromDb(): Flowable<List<RoomRepo>> {
        return appDatabase.repoDao().getTrendingRepos()
    }

    override fun getReposFromDb(language: String): Flowable<List<RoomRepo>> {
        return appDatabase.repoDao().getTrendingRepos(language)
    }

    override fun getLiveReposFromDb(): LiveData<List<RoomRepo>> {
        return appDatabase.repoDao().getLiveTrendingRepos()
    }
}