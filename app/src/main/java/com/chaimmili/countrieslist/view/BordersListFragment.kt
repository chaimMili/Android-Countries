package com.chaimmili.countrieslist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaimmili.countrieslist.view_model.CountriesViewModel
import com.chaimmili.countrieslist.R
import com.chaimmili.countrieslist.databinding.BordersFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BordersListFragment : Fragment() {

    private val countriesViewModel: CountriesViewModel by viewModels()
    private lateinit var binding: BordersFragmentBinding

    private val args: BordersListFragmentArgs by navArgs()
    lateinit var countriesListAdapter: CountriesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BordersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countriesListAdapter = CountriesListAdapter()
        activity?.title = getString(R.string.bordering_with, args.countryName)

        initUI()

        lifecycleScope.launch {
            countriesViewModel.getBorderingCountries(args.bordersCode.toList()).also {
                binding.progressBar.isVisible = false
                if (it.isEmpty())
                    Toast.makeText(
                        requireContext(),
                        "Not found any boards",
                        Toast.LENGTH_LONG
                    ).show()
                else
                    countriesListAdapter.addCountries(it)
            }
        }
    }

    private fun initUI() {
        binding.bordersList.apply {
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
    }
}