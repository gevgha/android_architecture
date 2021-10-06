package com.gevorg.mvvm.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.gevorg.mvvm.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    companion object {
        const val DATABASE_NAME = "mgs_indoor_database.db"
    }

//    @Provides
//    @Singleton
//    fun provideApiClient(application: Application): ApiClient {
//        val interceptor = HttpLoggingInterceptor()
//        if (BuildConfig.ENABLE_HTTP_LOG) {
//            interceptor.level = HttpLoggingInterceptor.Level.BODY
//        } else {
//            interceptor.level = HttpLoggingInterceptor.Level.NONE
//        }
//
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .addInterceptor { chain ->
//                val original = chain.request()
//
//                // Customize the request
//                val requestBuilder = original.newBuilder()
//                requestBuilder.addHeader("Content-Type", "application/json")
//
//                val request = requestBuilder.build()
//
//                val response = chain.proceed(request)
//                response.cacheResponse()
//                // Customize or return the response
//                response
//            }
//            .build()
//
//        val retrofit: Retrofit = Retrofit.Builder()
//            .baseUrl(BuildConfig.URL_BASE)
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        return ApiClient(application, retrofit)
//    }



//    @Provides
//    @Singleton
//    fun provideNavigineApiClient(application: Application) : NavigineApiClient {
//        val interceptor = HttpLoggingInterceptor()
//        if (BuildConfig.ENABLE_HTTP_LOG) {
//            interceptor.level = HttpLoggingInterceptor.Level.BODY
//        } else {
//            interceptor.level = HttpLoggingInterceptor.Level.NONE
//        }
//
//        val okHttpClient = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .addInterceptor { chain ->
//                val original = chain.request()
//
//                // Customize the request
//                val requestBuilder = original.newBuilder()
//                requestBuilder.addHeader("Content-Type", "application/json")
//
//                val request = requestBuilder.build()
//
//                val response = chain.proceed(request)
//                response.cacheResponse()
//                // Customize or return the response
//                response
//            }
//            .build()
//
//        val retrofit: Retrofit = Retrofit.Builder()
//            .baseUrl(BuildConfig.URL_BASE_NAVIGIN)
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//        return NavigineApiClient(application, retrofit)
//    }


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