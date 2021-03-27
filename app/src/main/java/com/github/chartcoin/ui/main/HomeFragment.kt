package com.github.chartcoin.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.github.chartcoin.R
import com.github.chartcoin.data.dto.BitcoinPricesDto
import com.github.chartcoin.data.response.ApiResult

import java.util.*

class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.prices.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ApiResult.Success -> onPricesResult(result.result)
                is ApiResult.Error -> onPricesError()
            }
        })

        getPrices()
    }

    private fun getPrices() {
        homeViewModel.getPrices()
    }

    private fun onPricesResult(prices: BitcoinPricesDto) {
        Toast.makeText(this.context, "RESULT SUCCESS", Toast.LENGTH_LONG).show()
    }

    private fun onPricesError() {
        Toast.makeText(this.context, "RESULT ERROR", Toast.LENGTH_LONG).show()
    }
}