package com.sixbynine.unitpricecalculator.shared.currency

import kotlinx.serialization.Serializable

@Serializable
data class Currency(val code: String) {
  val displayName: String
    get() = symbol.let { if (it == code) code else "$code ($it)" }
}

internal expect val supportedCurrencies: List<Currency>

expect val Currency.symbol: String

expect val defaultCurrency: Currency

expect fun Currency.format(amount: Double): String?