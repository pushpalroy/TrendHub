package com.example.trendhub.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.trendhub.data.model.Repo
import com.example.trendhub.data.repo.GithubRepo
import com.example.trendhub.data.services.NetworkResult
import com.example.trendhub.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var githubRepo: GithubRepo

    var dataLoading: MutableLiveData<Boolean> = MutableLiveData()
    var dataGithubRepo: MutableLiveData<List<Repo>> = MutableLiveData()

    fun loadMostTrendingRepoCoroutine(language: String, since: String, spokenLangCode: String) {
        dataLoading.value = true
        viewModelScope.launch {
            val response = githubRepo.getMostTrendingRepositories(language, since, spokenLangCode)
            dataLoading.value = false
            when (response) {
                is NetworkResult.Success -> {
                    dataGithubRepo.value = response.body
                }
                is NetworkResult.Failure -> {
                    Timber.e("onError")
                }
            }
        }
    }
}