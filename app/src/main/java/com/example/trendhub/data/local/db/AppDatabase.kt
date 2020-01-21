package com.example.trendhub.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trendhub.data.local.db.dao.RepoDao
import com.example.trendhub.data.local.db.model.RoomRepo

@Database(entities = [RoomRepo::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}