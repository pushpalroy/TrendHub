package com.example.trendhub.data.local.db

import com.example.trendhub.data.local.db.model.RoomRepo
import io.reactivex.Completable
import io.reactivex.Flowable

interface DbHelper {

    fun getMostTrendingRepositories(): Flowable<List<RoomRepo>>

    fun getMostTrendingRepositories(language: String): Flowable<List<RoomRepo>>

    fun saveRepository(repository: RoomRepo)

    fun saveAllRepositories(repositories: List<RoomRepo>): Completable

}