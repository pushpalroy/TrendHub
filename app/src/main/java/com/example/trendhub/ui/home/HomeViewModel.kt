package com.example.trendhub.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.trendhub.data.local.db.model.RoomRepo
import com.example.trendhub.data.model.Repo
import com.example.trendhub.data.repo.GithubRepo
import com.example.trendhub.data.services.NetworkResult
import com.example.trendhub.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var githubRepo: GithubRepo

    var dataLoading: MutableLiveData<Boolean> = MutableLiveData()
    var dataGithubRepoListRemote: MutableLiveData<List<Repo>> = MutableLiveData()
    var dataGithubRepoListLocal: MutableLiveData<List<RoomRepo>> = MutableLiveData()

    fun loadMostTrendingRepoCoroutine(language: String, since: String, spokenLangCode: String) {
        dataLoading.value = true
        viewModelScope.launch {
            val response = githubRepo.getMostTrendingReposCoroutine(language, since, spokenLangCode)
            dataLoading.value = false
            when (response) {
                is NetworkResult.Success -> {
                    dataGithubRepoListRemote.value = response.body
                }
                is NetworkResult.Failure -> {
                    Timber.e("onError")
                }
            }
        }
    }

    fun insertAllReposInDatabase(repositories: List<RoomRepo>) {
        addDisposable(
            githubRepo.insertAllReposInDatabase(repositories)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Timber.i("Success") },
                    { error -> Timber.e(error) })
        )
    }

    fun loadAllReposFromDatabase() {
        dataLoading.value = true
        val response = githubRepo.getMostTrendingReposFromDatabase()

        addDisposable(
            response
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dataGithubRepoListLocal.value = it },
                    { error -> Timber.e(error) })
        )
        dataLoading.value = false
    }

    fun convertModelTypeToRoom(repositories: List<Repo>): List<RoomRepo> {
        val repoList: ArrayList<RoomRepo> = ArrayList()
        var repoRoom: RoomRepo
        for (item in repositories) {
            repoRoom = RoomRepo(
                item.author,
                item.repoName,
                item.repoAvatar,
                item.repoUrl,
                item.description,
                item.language,
                item.languageColor,
                item.stars,
                item.forks,
                item.currentPeriodStars,
                item.contributors
            )

            repoList.add(repoRoom)
        }

        return repoList
    }

    fun convertModelTypeToRetrofit(repositories: List<RoomRepo>): List<Repo> {
        val repoList: ArrayList<Repo> = ArrayList()
        var repo: Repo
        for (item in repositories) {
            repo = Repo(
                item.author,
                item.repoName,
                item.repoAvatar,
                item.repoUrl,
                item.description,
                item.language,
                item.languageColor,
                item.stars,
                item.forks,
                item.currentPeriodStars,
                item.contributors
            )

            repoList.add(repo)
        }

        return repoList
    }
}