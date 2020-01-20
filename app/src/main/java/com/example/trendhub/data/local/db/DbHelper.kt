package com.example.trendhub.data.local.db

import com.example.trendhub.data.local.db.model.RoomRepo
import io.reactivex.Completable
import io.reactivex.Flowable

interface DbHelper {

    fun getReposFromDb(): Flowable<List<RoomRepo>>

    fun getReposFromDb(language: String): Flowable<List<RoomRepo>>

    fun saveRepo(repository: RoomRepo)

    fun saveRepos(repositories: List<RoomRepo>): Completable

}