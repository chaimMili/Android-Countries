package com.chaimmili.countrieslist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaimmili.countrieslist.view_model.CountriesOrderBy
import com.chaimmili.countrieslist.view_model.CountriesViewModel
import com.chaimmili.countrieslist.R
import com.chaimmili.countrieslist.databinding.CountriesListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountriesListFragment : Fragment() {

    private lateinit var binding: CountriesListFragmentBinding

    private val countriesViewModel: CountriesViewModel by viewModels()

    lateinit var countriesListAdapter: CountriesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CountriesListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        countriesListAdapter = CountriesListAdapter(onItemClicked)
        activity?.title = getString(R.string.main_title)

        initUI()
        initObservers()
    }

    private fun initUI() {
        binding.countriesList.apply {
            adapter = countriesListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        binding.progressBar.isVisible = true

        initOrderView()
    }

    private fun initOrderView() {
        val orderList = CountriesOrderBy.values()

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item, orderList.map { it.label }
        )

        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item)

        binding.orderSpinner.adapter = spinnerAdapter

        binding.orderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                lifecycleScope.launch {
                    countriesViewModel.getCountries(orderList[position])
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun initObservers() {
        countriesViewModel.countriesList.observe(viewLifecycleOwner, {
            binding.progressBar.isVisible = false
            countriesListAdapter.addCountries(it)
        })
    }

    private val onItemClicked: (String, List<String>) -> Unit =
        { name: String, bordersCodes: List<String> ->
            lifecycleScope.launch {
                CountriesListFragmentDirections.actionCountriesListFragmentToBordersListFragment(
                    name,
                    bordersCodes.toTypedArray()
                ).also {
                    findNavController().navigate(it)
                }
            }
        }
}