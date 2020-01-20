package com.example.trendhub.ui.home

import android.content.Context
import com.example.trendhub.injection.module.BaseActivityModule
import com.example.trendhub.injection.qualifiers.ActivityContext
import com.example.trendhub.injection.scope.ActivityScope
import dagger.Binds
import dagger.Module
import dagger.android.support.DaggerAppCompatActivity

@Module(includes = [BaseActivityModule::class])
abstract class HomeActivityModule {

    @Binds
    @ActivityContext
    abstract fun provideActivityContext(activity: HomeActivity): Context

    @Binds
    @ActivityScope
    abstract fun provideActivity(homeActivity: HomeActivity): DaggerAppCompatActivity
}