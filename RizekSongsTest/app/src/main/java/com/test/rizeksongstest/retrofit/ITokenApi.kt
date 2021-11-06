package com.test.rizeksongstest.retrofit

import com.test.rizeksongstest.models.AccessToken
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface ITokenApi {
    @Headers("Authorization: Basic MTFmN2U2ZmVjYTU2NDEzY2JjZGNjOWI0NmIxZmE1MGY6ZGMwZWMxM2JlNTM4NGU4ZWIyNGY0N2ZlYTZmMDBmYzg=", "Content-Type: application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/api/token")
    fun fetchToken(@Field("grant_type") type: String = "client_credentials"): Observable<AccessToken>
}