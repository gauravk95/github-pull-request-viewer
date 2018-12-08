package com.github.pullrequest.data.source.db

import android.arch.persistence.room.TypeConverter
import com.github.pullrequest.data.models.local.Label
import com.google.gson.Gson

import java.util.Date

object Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun labelToJson(value: Label?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToUser(value: String): Label? {
        return Gson().fromJson(value, Label::class.java) as Label
    }
}