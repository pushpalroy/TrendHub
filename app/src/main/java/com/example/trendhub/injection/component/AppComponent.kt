package com.example.trendhub.injection.component

import android.content.Context
import com.example.trendhub.BaseApplication
import com.example.trendhub.injection.module.ActivityBindingModule
import com.example.trendhub.injection.module.AppModule
import com.example.trendhub.injection.module.ViewModelFactoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelFactoryModule::class, AndroidSupportInjectionModule::class, ActivityBindingModule::class])
interface AppComponent : AndroidInjector<BaseApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}
