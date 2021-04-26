package com.chaimmili.countrieslist.model.api

import com.chaimmili.countrieslist.model.data.Country
import retrofit2.http.GET
import retrofit2.http.Query

interface CountryApi {

    @GET("rest/v2/all")
    suspend fun getAllCountries(
        @Query("filter") filter: String = "name;nativeName;flag;borders;area;alpha3Code"
    ): List<Country>
}