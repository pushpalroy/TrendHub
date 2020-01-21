package com.example.trendhub.data.local.db

import androidx.lifecycle.LiveData
import com.example.trendhub.data.local.db.model.RoomRepo
import io.reactivex.Completable
import io.reactivex.Flowable

interface DbHelper {

    fun getReposFromDb(): Flowable<List<RoomRepo>>

    fun getReposFromDb(language: String): Flowable<List<RoomRepo>>

    fun saveRepoInDb(repository: RoomRepo)

    fun saveReposInDb(repositories: List<RoomRepo>): Completable

    fun getLiveReposFromDb(): LiveData<List<RoomRepo>>
}