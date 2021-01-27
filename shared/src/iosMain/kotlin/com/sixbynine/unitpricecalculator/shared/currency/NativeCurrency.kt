package com.sixbynine.unitpricecalculator.shared.currency

import platform.Foundation.*

internal actual val supportedCurrencies: List<Currency>
  get() {
    return NSLocale.ISOCurrencyCodes.mapNotNull { it as? String }.map { Currency(it) }
  }

actual val Currency.symbol: String
  get() {
    val locale = NSLocale.currentLocale
    return if (locale.currencyCode == this.code) {
      // Bizarrely, Locale.currencySymbol can be different than
      // Locale.displayNameForKey(NSLocaleCurrencySymbol, code). E.g. in fr_CA,
      // currencySymbol is "$", but displayNameForKey(NSLocaleCurrencySymbol, "CAD") is
      // "CA$".
      locale.currencySymbol
    } else {
      locale.displayNameForKey(NSLocaleCurrencySymbol, code) ?: code
    }
  }

actual val defaultCurrency: Currency
  get() {
    return Currency(NSLocale.currentLocale.currencyCode ?: "USD")
  }

actual fun Currency.format(amount: Double): String? {
  return NSNumberFormatter().also {
    it.numberStyle = NSNumberFormatterCurrencyStyle
    it.currencyCode = this.code
  }.stringFromNumber(NSNumber(amount))
}