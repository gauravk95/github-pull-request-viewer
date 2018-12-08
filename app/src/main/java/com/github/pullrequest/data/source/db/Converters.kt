package com.github.pullrequest.data.source.db

import android.arch.persistence.room.TypeConverter
import com.github.pullrequest.data.models.local.Label
import com.github.pullrequest.data.models.local.User
import com.google.gson.Gson

import java.util.Date

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun userToJson(value: User): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToUser(value: String?): User {
        return Gson().fromJson(value, User::class.java) as User
    }

    @TypeConverter
    fun labelToJson(value: List<Label>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToLabel(value: String?): List<Label> {
        val objects = Gson().fromJson(value, Array<Label>::class.java) as Array<Label>
        return objects.toList()
    }
}