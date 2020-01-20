package com.example.trendhub.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trendhub.R
import com.example.trendhub.databinding.ActivityHomeBinding
import com.example.trendhub.ui.base.BaseActivity

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(),
    HomeAdapter.OnRepoClickListener {

    private val mAdapter = HomeAdapter(arrayListOf(), this)

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

    override fun layoutId(): Int = R.layout.activity_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpUi()
        setUpObservers()
    }

    private fun setUpUi() {
        setSupportActionBar(binding.toolbar)
        binding.rvRepository.layoutManager = LinearLayoutManager(this)
        binding.rvRepository.adapter = mAdapter
        binding.rvRepository.itemAnimator = DefaultItemAnimator()
        binding.rvRepository.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setUpObservers() {
        // Loading data from API
        viewModel.loadMostTrendingRepoCoroutine("", "weekly", "english")

        // On loader data changed
        viewModel.dataLoading.observe(this, Observer {
            handleVisibility(it)
        })

        // On data loaded from API
        viewModel.dataGithubRepoListRemote.observe(this, Observer { it ->
            it?.let {
                viewModel.insertAllReposInDatabase(viewModel.convertModelTypeToRoom(it))
            }
        })

        // Load data from local database
        viewModel.loadAllReposFromDatabase()

        // On local data loaded
        viewModel.dataGithubRepoListLocal.observe(this, Observer { it ->
            it?.let {
                mAdapter.setRepoList(viewModel.convertModelTypeToRetrofit(it))
            }
        })
    }

    private fun handleVisibility(loading: Boolean) {
        binding.progressbar.visibility = if (loading) View.VISIBLE else View.INVISIBLE
    }

    override fun onRepoClick(position: Int) {
        Toast.makeText(this, "Repo Clicked: $position", Toast.LENGTH_LONG).show()
    }
}