package com.chaimmili.countrieslist.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaimmili.countrieslist.model.repository.CountriesRepository
import com.chaimmili.countrieslist.model.data.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val countriesRepository: CountriesRepository
) : ViewModel() {

    val countriesList = MutableLiveData<List<Country>>()

    init {
        viewModelScope.launch {
            countriesRepository.refreshCountries()
        }
    }

    suspend fun getCountries(order: CountriesOrderBy) {
        countriesRepository.getCountriesByOrder(order).onEach {
            countriesList.value = it
        }.launchIn(viewModelScope)
    }

    suspend fun getBorderingCountries(countriesCodes: List<String>): List<Country> =
        countriesRepository.getBorderingCountries(countriesCodes)

}

enum class CountriesOrderBy(val orderBy: String, val label: String) {
    NAME_ASCENDING("name_ascending", "Name ascending order"),
    NAME_DESCENDING("name_descending", "Name descending order"),
    AREA_ASCENDING("area_ascending", "Area ascending order"),
    AREA_DESCENDING("area_descending", "Area descending order"),
}