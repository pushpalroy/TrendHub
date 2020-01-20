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
     * @param language Programming language of repository: Optional
     * @param since default to daily, possible values: daily, weekly and monthly: Optional
     * @param spokenLangCode spoken language: Optional
     */
    fun loadReposFromApi(language: String, since: String, spokenLangCode: String) {
        liveDataLoading.value = true
        viewModelScope.launch {
            when (val response = githubRepo.getReposFromApi(language, since, spokenLangCode)) {
                is NetworkResult.Success -> {
                    liveDataRepoListRemote.value = response.body
                    liveDataLoading.value = false
                }
                is NetworkResult.Failure -> {
                    Timber.e("onError")
                    liveDataLoading.value = false
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
                    { error ->
                        Timber.e(error)
                        liveDataLoading.value = false
                    })
        )
    }

    /**
     * Insert repos in local DB
     * @param repositories List of repositories
     */
    fun insertReposInDb(repositories: List<RoomRepo>) {
        liveDataLoading.value = true
        addDisposable(
            githubRepo.insertReposInDb(repositories)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Timber.i("Success")
                        liveDataLoading.value = false
                    },
                    { error ->
                        Timber.e(error)
                        liveDataLoading.value = false
                    })
        )
    }

    /**
     * Convert: List<Repo> -> List<RoomRepo>
     * @param repositories List of repositories
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
     * @param repositories List of repositories
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