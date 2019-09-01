package com.tktkokym.currencyviewer.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {
    @TypeConverter
    fun fromMap(map: Map<String, Double>): String {
        return Gson().toJson(map)
    }

    @TypeConverter
    fun fromString(value: String): Map<String, Double> {
        val mapType = object : TypeToken<Map<String, Double>>(){}.type
        return Gson().fromJson(value, mapType)
    }
}