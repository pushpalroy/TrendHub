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

    var liveDataLoading: MutableLiveData<Boolean> = MutableLiveData()
    var liveDataRepoListRemote: MutableLiveData<List<Repo>> = MutableLiveData()
    var liveDataRepoListDb: MutableLiveData<List<RoomRepo>> = MutableLiveData()

    /**
     * Load repos from API
     */
    fun loadReposFromApi(language: String, since: String, spokenLangCode: String) {
        liveDataLoading.value = true
        viewModelScope.launch {
            val response = githubRepo.getReposFromApi(language, since, spokenLangCode)
            when (response) {
                is NetworkResult.Success -> {
                    liveDataRepoListRemote.value = response.body
                    liveDataLoading.value = false
                }
                is NetworkResult.Failure -> {
                    Timber.e("onError")
                }
            }
        }
    }

    /**
     * Load repos from local DB
     */
    fun loadReposFromDb() {
        liveDataLoading.value = true
        val response = githubRepo.getReposFromDb()

        addDisposable(
            response
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        liveDataRepoListDb.value = it
                        liveDataLoading.value = false
                    },
                    { error -> Timber.e(error) })
        )
    }

    /**
     * Load repo in local DB
     */
    fun insertReposInDb(repositories: List<RoomRepo>) {
        addDisposable(
            githubRepo.insertReposInDb(repositories)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { Timber.i("Success") },
                    { error -> Timber.e(error) })
        )
    }

    /**
     * Convert: List<Repo> -> List<RoomRepo>
     */
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

    /**
     * Convert: List<RoomRepo> -> List<Repo>
     */
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