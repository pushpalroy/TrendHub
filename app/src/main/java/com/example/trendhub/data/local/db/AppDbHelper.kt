package com.example.trendhub.data.local.db

import com.example.trendhub.data.local.db.model.RoomRepo
import io.reactivex.Completable
import io.reactivex.Flowable

class AppDbHelper constructor(appDatabase: AppDatabase) : DbHelper {

    private val mAppDatabase: AppDatabase = appDatabase

    override fun getReposFromDb(): Flowable<List<RoomRepo>> {
        return mAppDatabase.repoDao().getTrendingRepos()
    }

    override fun getReposFromDb(language: String): Flowable<List<RoomRepo>> {
        return mAppDatabase.repoDao().getTrendingRepos(language)
    }

    override fun saveRepo(repository: RoomRepo) {
        mAppDatabase.repoDao().saveRepo(repository)
    }

    override fun saveRepos(repositories: List<RoomRepo>): Completable {
        return mAppDatabase.repoDao().saveRepos(*repositories.toTypedArray())
    }
}