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
import com.github.aachartmodel.aainfographics.aachartcreator.aa_toAAOptions
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AADataLabels
import com.github.aachartmodel.aainfographics.aaoptionsmodel.AATooltip
import com.github.chartcoin.R
import com.github.chartcoin.data.dto.BitcoinPricesDto
import com.github.chartcoin.data.response.ApiResult
import kotlinx.android.synthetic.main.home_fragment.*
import java.text.NumberFormat
import java.time.LocalDate
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
                is ApiResult.Success -> onPricesResult(result.result)
                is ApiResult.Error -> onPricesError()
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

        homeViewModel.getPrices()
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
}