package com.sixbynine.unitpricecalculator.shared.locale

import kotlinx.serialization.Serializable

@Serializable
data class Locale(val language: String, val country: String)

expect fun currentLocale(): Locale