package com.example.trendhub.data.local.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trendhub.data.local.db.model.RoomRepo.Companion.ROOM_REPO
import com.example.trendhub.data.model.Contributor

@Entity(tableName = ROOM_REPO)
data class RoomRepo(
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "name")
    val repoName: String,
    @ColumnInfo(name = "avatar")
    val repoAvatar: String,
    @ColumnInfo(name = "url")
    val repoUrl: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "language")
    val language: String,
    @ColumnInfo(name = "languageColor")
    val languageColor: String,
    @ColumnInfo(name = "stars")
    val stars: Int,
    @ColumnInfo(name = "forks")
    val forks: Int,
    @ColumnInfo(name = "currentPeriodStars")
    val currentPeriodStars: Int,
    @ColumnInfo(name = "builtBy")
    val contributors: List<Contributor>
) {
    companion object {
        const val ROOM_REPO = "repo_table"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}