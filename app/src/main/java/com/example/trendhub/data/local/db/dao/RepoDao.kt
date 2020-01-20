package com.example.trendhub.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.trendhub.data.local.db.model.RoomRepo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface RepoDao {

    @Query("SELECT * FROM repo_table")
    fun getMostTrendingRepositories(): Flowable<List<RoomRepo>>

    @Query("SELECT * FROM repo_table WHERE language = :language")
    fun getMostTrendingRepositories(language: String): Flowable<List<RoomRepo>>

    @Insert
    fun saveRepository(repository: RoomRepo)

    @Insert
    fun saveAllRepositories(vararg repositories: RoomRepo): Completable

    @Delete
    fun deleteAllRepositories(repositories: List<RoomRepo>): Single<Int>
}