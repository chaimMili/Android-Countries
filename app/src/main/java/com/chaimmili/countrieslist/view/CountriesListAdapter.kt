package com.chaimmili.countrieslist.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chaimmili.countrieslist.databinding.CountryCardBinding
import com.chaimmili.countrieslist.extantions.loadSvg
import com.chaimmili.countrieslist.model.data.Country

class CountriesListAdapter(
    private val onClickEvent: (String, List<String>) -> Unit = {_: String, _: List<String> -> }
) : RecyclerView.Adapter<CountriesListAdapter.CountryViewHolder>() {

    private val countriesList: MutableList<Country> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder =
        CountryViewHolder(CountryCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.initCountry(countriesList[position])
    }

    override fun getItemCount(): Int = countriesList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addCountries(countries: List<Country>) {
        countriesList.clear()
        countriesList.addAll(countries)
        notifyDataSetChanged()
    }

    inner class CountryViewHolder(
        private val binding: CountryCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun initCountry(countryDetails: Country) {
            binding.flag.loadSvg(countryDetails.flag)
            binding.nativeName.text = countryDetails.nativeName
            binding.name.text = countryDetails.name

            binding.countryCardContainer.setOnClickListener {
                onClickEvent(countryDetails.name, countryDetails.borders)
            }
        }
    }
}