package com.example.trendhub.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contributor(
    @SerializedName("username")
    val username: String,
    @SerializedName("href")
    val profileUrl: String,
    @SerializedName("avatar")
    val avatar: String
) : Parcelable