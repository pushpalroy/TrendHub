package com.example.trendhub.data.local.db

import androidx.room.TypeConverter
import com.example.trendhub.data.model.Contributor
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun listToJson(list: List<Contributor?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun jsonToList(value: String?): List<Contributor> {
        val listType = object : TypeToken<List<Contributor?>?>() {}.type
        return Gson().fromJson(value, listType)
    }
}