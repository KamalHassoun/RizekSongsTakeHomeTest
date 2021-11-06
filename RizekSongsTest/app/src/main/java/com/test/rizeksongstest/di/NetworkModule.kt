package com.test.rizeksongstest.di

import com.google.gson.GsonBuilder
import com.test.rizeksongstest.helpers.SpotifyApiInterceptor
import com.test.rizeksongstest.retrofit.ISpotifyApi
import com.test.rizeksongstest.retrofit.ITokenApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun providesSpotifyInterceptor(): SpotifyApiInterceptor {
        return SpotifyApiInterceptor()
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(interceptor: SpotifyApiInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun providesSpotifyApi(okHttpClient: OkHttpClient): ISpotifyApi {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(ISpotifyApi::class.java)
    }

    @Singleton
    @Provides
    fun providesTokenApi(): ITokenApi {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
        val gson = GsonBuilder().create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://accounts.spotify.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(ITokenApi::class.java)
    }
}