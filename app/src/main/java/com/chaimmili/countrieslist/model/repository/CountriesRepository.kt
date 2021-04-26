package com.chaimmili.countrieslist.model.repository

import com.chaimmili.countrieslist.model.api.CountryApi
import com.chaimmili.countrieslist.model.data.Country
import com.chaimmili.countrieslist.model.db.CountryDao
import com.chaimmili.countrieslist.view_model.CountriesOrderBy
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    private val countryApi: CountryApi,
    private val countryDao: CountryDao
) : CountriesRepository {

    override suspend fun getCountries(): Flow<List<Country>> {
        refreshCountries()

        return  getCountriesByOrder(CountriesOrderBy.NAME_ASCENDING)
    }

    override suspend fun getCountriesByOrder(order: CountriesOrderBy): Flow<List<Country>> =
        countryDao.getAll(order.orderBy)

    override suspend fun getBorderingCountries(countriesCodes: List<String>): List<Country> =
        countryDao.getBordersCountry(countriesCodes)


    private suspend fun refreshCountries() {
        countryDao.insertAll(*(countryApi.getAllCountries()).toTypedArray())
    }
}

interface CountriesRepository {
    suspend fun getCountries(): Flow<List<Country>>

    suspend fun getCountriesByOrder(order: CountriesOrderBy): Flow<List<Country>>

    suspend fun getBorderingCountries(countriesCodes: List<String>): List<Country>
}