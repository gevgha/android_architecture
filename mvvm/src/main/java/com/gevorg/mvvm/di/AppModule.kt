package com.gevorg.mvvm.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.gevorg.mvvm.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    companion object {
        const val DATABASE_NAME = "mgs_indoor_database.db"
    }
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(
            BuildConfig.APPLICATION_ID + ".prefs", Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

}