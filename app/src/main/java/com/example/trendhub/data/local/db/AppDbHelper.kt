package com.example.trendhub.data.local.db

import com.example.trendhub.data.local.db.model.RoomRepo
import io.reactivex.Completable
import io.reactivex.Flowable

class AppDbHelper constructor(appDatabase: AppDatabase) : DbHelper {

    private val mAppDatabase: AppDatabase = appDatabase

    override fun getMostTrendingRepositories(): Flowable<List<RoomRepo>> {
        return mAppDatabase.repoDao().getMostTrendingRepositories()
    }

    override fun getMostTrendingRepositories(language: String): Flowable<List<RoomRepo>> {
        return mAppDatabase.repoDao().getMostTrendingRepositories(language)
    }

    override fun saveRepository(repository: RoomRepo) {
        mAppDatabase.repoDao().saveRepository(repository)
    }

    override fun saveAllRepositories(repositories: List<RoomRepo>): Completable {
        return mAppDatabase.repoDao().saveAllRepositories(*repositories.toTypedArray())
    }
}