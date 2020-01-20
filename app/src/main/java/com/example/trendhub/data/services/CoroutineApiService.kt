package com.example.trendhub.data.services

import com.example.trendhub.data.model.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoroutineApiService {
    @GET("/repositories")
    suspend fun getTrendingRepositories(
        @Query("language") language: String,
        @Query("since") since: String,
        @Query("spoken_language_code") spokenLangCode: String
    ): Response<List<Repo>>
}