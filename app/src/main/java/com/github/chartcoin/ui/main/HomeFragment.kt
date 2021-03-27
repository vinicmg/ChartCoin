package com.github.chartcoin.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement
import com.github.aachartmodel.aainfographics.aatools.AAColor
import com.github.chartcoin.R
import com.github.chartcoin.data.dto.BitcoinPricesDto
import com.github.chartcoin.data.dto.CoinCurrencysDto
import com.github.chartcoin.data.response.ApiResponse
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.NumberFormat
import java.time.format.DateTimeFormatter

import java.util.*

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.home_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.prices.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ApiResponse.Success -> onPricesResult(result.result)
                is ApiResponse.Error -> onPricesError()
            }
        })

        homeViewModel.coins.observe(viewLifecycleOwner, Observer {
            result ->
            when(result) {
                is ApiResponse.Success -> onCoinsSuccess(result.result)
                is ApiResponse.Error -> onCoinsErrors()
            }
        })

        refreshHome.setOnRefreshListener { this.getPrices() }

        getPrices()
    }

    private fun getPrices() {
        progressBarDestaque.visibility = View.VISIBLE
        layoutDestaque.visibility = View.GONE
        layoutChart.visibility = View.GONE
        refreshHome.isRefreshing = false

        getCurrency()
        homeViewModel.getPrices()
    }

    private fun getCurrency() {
        homeViewModel.getCurrencys()
    }

    private fun onPricesResult(prices: BitcoinPricesDto) {
        val lastPosition = prices.values.size - 1

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale("US", "US"))
        valorBitcoinDestaque.text = formatCurrency.format(prices.values[lastPosition].y)

        loadChart(prices)

        progressBarDestaque.visibility = View.INVISIBLE
        layoutDestaque.visibility = View.VISIBLE
    }

    private fun onPricesError() {
        Toast.makeText(
            this.context,
            "Ocorreu um erro ao tentar buscar os dados!",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun loadChart(prices: BitcoinPricesDto) {
        val formatterDate = DateTimeFormatter.ofPattern("dd/MM/yy")
        val chartModel: AAChartModel = AAChartModel()
            .colorsTheme(arrayOf(AAColor.Orange))
            .chartType(AAChartType.Spline)
            .title("Variação de Bitcoins")
            .yAxisTitle("Valores")
            .categories(
                prices.values.map { it.x.format(formatterDate) }.toTypedArray()
            )
            .series(
                arrayOf(
                    AASeriesElement().name("Bitcoin")
                        .data(prices.values.map { it.y }.toTypedArray())
                )
            )

        layoutChart.visibility = View.VISIBLE
        aaChartView.aa_drawChartWithChartModel(chartModel)
    }

    private fun onCoinsSuccess(result: CoinCurrencysDto) {
        Toast.makeText(this.context, result.rates.BRL.toString(), Toast.LENGTH_LONG).show()
    }

    private fun onCoinsErrors() {
        Toast.makeText(this.context, "ERRO", Toast.LENGTH_LONG).show()
    }
}