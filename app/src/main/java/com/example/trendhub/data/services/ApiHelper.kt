package com.example.trendhub.data.services

import com.example.trendhub.data.model.Repo

interface ApiHelper {

    suspend fun getReposFromApi(
        language: String, since: String, spokenLangCode: String
    ): NetworkResult<List<Repo>>
}