package com.test.rizeksongstest.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("access_token")
    @Expose
    val token: String,

    @SerializedName("token_type")
    @Expose
    val type: String
)
