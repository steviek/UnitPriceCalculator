package com.sixbynine.unitpricecalculator.shared.manager

import com.sixbynine.unitpricecalculator.shared.concurrent.*
import com.sixbynine.unitpricecalculator.shared.currency.Currency
import com.sixbynine.unitpricecalculator.shared.currency.defaultCurrency
import com.sixbynine.unitpricecalculator.shared.currency.supportedCurrencies
import com.sixbynine.unitpricecalculator.shared.preferences
import com.sixbynine.unitpricecalculator.shared.time.Stopwatch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromHexString
import kotlinx.serialization.encodeToHexString
import kotlinx.serialization.protobuf.ProtoBuf

@OptIn(ExperimentalSerializationApi::class)
object CurrencyManager {

  private const val prefsKey = "currency"

  private val _current = nullableAtomicRef<Currency>().frozen()

  var current: Currency
    get() {
      _current.get()?.let { return it }
      preferences.getString(prefsKey)?.let { encoded ->
        val decoded = ProtoBuf.decodeFromHexString<Currency>(encoded).frozen()
        _current.set(decoded)
        return decoded
      }
      val fallback = defaultCurrency.frozen()
      _current.set(fallback)
      return fallback
    }
    set(value) {
      preferences.putString(prefsKey, ProtoBuf.encodeToHexString(value))
      _current.set(value.frozen())
    }
}