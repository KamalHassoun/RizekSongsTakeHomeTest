package com.test.rizeksongstest.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Album(
    @SerializedName("images")
    @Expose
    val images: List<Image>
): Parcelable
