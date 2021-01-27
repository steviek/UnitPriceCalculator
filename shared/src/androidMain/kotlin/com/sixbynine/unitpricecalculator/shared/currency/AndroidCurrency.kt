package com.sixbynine.unitpricecalculator.shared.currency

import java.text.NumberFormat
import java.util.*

internal actual val supportedCurrencies: List<Currency>
  get() {
    return java.util.Currency.getAvailableCurrencies().map { Currency(it.currencyCode) }
  }

actual val Currency.symbol: String
  get() {
    return java.util.Currency.getInstance(code).symbol
  }

actual val defaultCurrency: Currency
  get() = Currency(java.util.Currency.getInstance(Locale.getDefault()).currencyCode)

actual fun Currency.format(amount: Double): String? {
  return NumberFormat.getCurrencyInstance()
    .also {
      it.currency = java.util.Currency.getInstance(this.code)
    }
    .format(amount)
}