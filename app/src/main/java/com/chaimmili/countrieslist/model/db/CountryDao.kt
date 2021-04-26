package com.chaimmili.countrieslist.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.chaimmili.countrieslist.model.data.Country
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    @Query("""SELECT * FROM countries ORDER BY 
        CASE WHEN :order = 'name_ascending' THEN name END,
        CASE WHEN :order = 'name_descending' THEN name END DESC,
        CASE WHEN :order = 'area_ascending' THEN area END,
        CASE WHEN :order = 'area_descending' THEN area END DESC"""
    )
    fun getAll(order: String): Flow<List<Country>>

    @Query("SELECT * FROM countries WHERE alpha3_code IN (:bordersCode)")
    suspend fun getBordersCountry(bordersCode: List<String>): List<Country>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(vararg countries: Country)
}