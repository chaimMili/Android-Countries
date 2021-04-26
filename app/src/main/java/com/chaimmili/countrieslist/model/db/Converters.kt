package com.chaimmili.countrieslist.model.db

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object Converters {

    private lateinit var moshi: Moshi

    fun initialize(moshi: Moshi) {
        this.moshi = moshi
    }

    private val parameterizedType = Types.newParameterizedType(List::class.java, String::class.java)

    @TypeConverter
    fun fromList(list: List<String>): String =
        moshi.adapter<List<String>>(parameterizedType).toJson(list)

    @TypeConverter
    fun toList(item: String): List<String> {

        return moshi.adapter<List<String>>(parameterizedType).fromJson(item) ?: emptyList()
    }
}