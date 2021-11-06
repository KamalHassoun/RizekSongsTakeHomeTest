package com.test.rizeksongstest.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Track(
    @SerializedName("name")
    @Expose
    val name: String,

    @SerializedName("uri")
    @Expose
    val uri: String,

    @SerializedName("album")
    @Expose
    val album: Album,

    @SerializedName("artists")
    @Expose
    val artists: List<Artist>
): Parcelable
