package com.chaimmili.countrieslist.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chaimmili.countrieslist.model.data.Country

@Database(entities = [Country::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun countryDao(): CountryDao
}