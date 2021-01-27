package com.sixbynine.unitpricecalculator.shared.locale

actual fun currentLocale(): Locale {
  return java.util.Locale.getDefault().toCommonLocale()
}

fun java.util.Locale.toCommonLocale(): Locale {
  return Locale(country = this.country, language = this.language)
}