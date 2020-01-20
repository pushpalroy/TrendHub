package com.example.trendhub.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repo(
    @SerializedName("author")
    val author: String,
    @SerializedName("name")
    val repoName: String,
    @SerializedName("avatar")
    val repoAvatar: String,
    @SerializedName("url")
    val repoUrl: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("languageColor")
    val languageColor: String,
    @SerializedName("stars")
    val stars: Int,
    @SerializedName("forks")
    val forks: Int,
    @SerializedName("currentPeriodStars")
    val currentPeriodStars: Int,
    @SerializedName("builtBy")
    val contributors: ArrayList<out Contributor>
) : Parcelable