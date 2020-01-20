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
    fun getTrendingRepos(): Flowable<List<RoomRepo>>

    @Query("SELECT * FROM repo_table WHERE language = :language")
    fun getTrendingRepos(language: String): Flowable<List<RoomRepo>>

    @Insert
    fun saveRepo(repository: RoomRepo)

    @Insert
    fun saveRepos(vararg repositories: RoomRepo): Completable

    @Delete
    fun deleteRepos(repositories: List<RoomRepo>): Single<Int>
}