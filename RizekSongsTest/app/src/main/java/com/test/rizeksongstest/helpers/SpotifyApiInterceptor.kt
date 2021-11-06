package com.test.rizeksongstest.helpers

import com.test.rizeksongstest.models.AccessToken
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotifyApiInterceptor @Inject constructor(): Interceptor {
    private var token: AccessToken? = null

    fun setToken(token: AccessToken) {
        this.token = token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request()
            .newBuilder()
            .addHeader("Authorization", token?.type + ' ' + token?.token)
            .addHeader("Content-Type", "application/json")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }


}