package com.sixbynine.unitpricecalculator.shared

expect fun Char.isDigitCommon(): Boolean

fun String.parseDouble(): Double? {
  val separator = firstOrNull { !it.isDigitCommon() } ?: return this.toDoubleOrNull()
  return this.replace(separator, '.').toDoubleOrNull()
}
