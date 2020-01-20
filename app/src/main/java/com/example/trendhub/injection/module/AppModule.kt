package com.example.trendhub.injection.module

import android.content.Context
import androidx.room.Room
import com.example.trendhub.AppConstants
import com.example.trendhub.BuildConfig
import com.example.trendhub.data.local.db.AppDatabase
import com.example.trendhub.data.local.db.AppDbHelper
import com.example.trendhub.data.repo.GithubRepo
import com.example.trendhub.data.services.CoroutineApiService
import com.example.trendhub.injection.qualifiers.ApplicationContext
import com.example.trendhub.injection.qualifiers.DatabaseInfo
import com.example.trendhub.utils.IRxSchedulers
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @DatabaseInfo
    internal fun provideDatabaseName(): String {
        return AppConstants.DB_NAME
    }

    @Provides
    @Singleton
    fun provideAppDbHelper(appDatabase: AppDatabase): AppDbHelper {
        return AppDbHelper(appDatabase)
    }

    @Provides
    @Singleton
    internal fun provideAppDatabase(@DatabaseInfo dbName: String, @ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpBuilder.interceptors()
                .add(httpLoggingInterceptor)
        }
        return httpBuilder.build()
    }

    @Provides
    @Singleton
    @Named(AppConstants.COROUTINE_RETROFIT)
    internal fun provideCoroutineRestAdapter(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideCoroutineApiService(@Named(AppConstants.COROUTINE_RETROFIT) restAdapter: Retrofit): CoroutineApiService {
        return restAdapter.create(CoroutineApiService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideCoroutineGithubRepo(
        coroutineApiService: CoroutineApiService,
        appDbHelper: AppDbHelper
    ): GithubRepo {
        return GithubRepo(coroutineApiService, appDbHelper)
    }

    @Provides
    @Singleton
    internal fun provideRxSchedulers(): IRxSchedulers {
        return object : IRxSchedulers {
            override fun main() = AndroidSchedulers.mainThread()
            override fun io() = Schedulers.io()
        }
    }
}
