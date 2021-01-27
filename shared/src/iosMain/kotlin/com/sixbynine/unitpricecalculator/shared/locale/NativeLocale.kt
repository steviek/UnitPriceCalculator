package com.sixbynine.unitpricecalculator.shared.locale

import platform.Foundation.NSLocale
import platform.Foundation.countryCode
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

actual fun currentLocale(): Locale {
  val current = NSLocale.currentLocale
  return Locale(country = current.countryCode ?: "", language = current.languageCode)
}