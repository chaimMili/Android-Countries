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

    override suspend fun refreshCountries() {
        countryDao.insertAll(*(countryApi.getAllCountries()).toTypedArray())
    }

    override suspend fun getCountriesByOrder(order: CountriesOrderBy): Flow<List<Country>> =
        countryDao.getAll(order.orderBy)

    override suspend fun getBorderingCountries(countriesCodes: List<String>): List<Country> =
        countryDao.getBordersCountry(countriesCodes)

}

interface CountriesRepository {
    suspend fun refreshCountries()
    suspend fun getCountriesByOrder(order: CountriesOrderBy): Flow<List<Country>>

    suspend fun getBorderingCountries(countriesCodes: List<String>): List<Country>
}