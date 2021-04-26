package com.chaimmili.countrieslist.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "countries")
data class Country(
    @PrimaryKey val name: String,
    @ColumnInfo(name = "native_name") val nativeName: String,
    val flag: String,
    val borders: List<String>,
    val area: Double?,
    @ColumnInfo(name = "alpha3_code") val alpha3Code: String
)