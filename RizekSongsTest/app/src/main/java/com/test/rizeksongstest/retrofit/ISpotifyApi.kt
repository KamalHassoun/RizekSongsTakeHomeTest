package com.test.rizeksongstest.retrofit

import com.test.rizeksongstest.models.SpotifyResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ISpotifyApi {
    @GET("search?type=track")
    fun getSongs(@Query("q") search: String): Observable<SpotifyResult>
}