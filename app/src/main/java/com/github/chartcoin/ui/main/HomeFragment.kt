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
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.format.DateTimeFormatter

import java.util.*

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private var moeda : Double = 1.0
    private var cotacaoAtual: Double = 0.0
    private var variacaoBitcoin: String = ""
    private lateinit var currencysDto : CoinCurrencysDto

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

        homeViewModel.coins.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is ApiResponse.Success -> onCoinsSuccess(result.result)
                is ApiResponse.Error -> onCoinsErrors()
            }
        })

        refreshHome.setOnRefreshListener { this.getPrices() }
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            moeda = when(checkedId) {
                chipBRL.id -> currencysDto.rates.BRL
                chipEUR.id -> currencysDto.rates.EUR
                else -> currencysDto.rates.USD
            }

            setCotacaoText()
        }

        getPrices()
    }

    private fun setCotacaoText() {
        val formatCurrency = when(chipGroup.checkedChipId) {
            chipBRL.id -> NumberFormat.getCurrencyInstance(Locale("PT", "BR"))
            chipEUR.id -> NumberFormat.getCurrencyInstance(Locale("EN", "FR"))
            else -> NumberFormat.getCurrencyInstance(Locale("US", "US"))
        }

        valorBitcoinDestaque.text = formatCurrency.format(cotacaoAtual * moeda)
        valorVariacao.text = variacaoBitcoin
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

        cotacaoAtual = prices.values[lastPosition].y
        val df = DecimalFormat("#,##%")
        df.roundingMode= RoundingMode.CEILING
        variacaoBitcoin = df.format((((prices.values[lastPosition].y / prices.values[lastPosition - 1].y)-1) * 100))
        setCotacaoText()

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

    private fun onCoinsSuccess(coin: CoinCurrencysDto) {
        currencysDto = coin
    }

    private fun onCoinsErrors() {
        Toast.makeText(
            this.context,
            "Ocorreu um erro ao tentar buscar os dados!",
            Toast.LENGTH_LONG
        ).show()
    }
}