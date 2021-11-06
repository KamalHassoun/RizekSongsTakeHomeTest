package com.test.rizeksongstest.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SpotifyResult(
    @SerializedName("tracks")
    @Expose
    val result: SpotifyResultList
)

data class SpotifyResultList(
    @SerializedName("items")
    @Expose
    val tracks: List<Track>
)
