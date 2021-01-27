package com.sixbynine.unitpricecalculator.shared.manager

import com.sixbynine.unitpricecalculator.shared.concurrent.frozen
import com.sixbynine.unitpricecalculator.shared.concurrent.nullableAtomicRef
import com.sixbynine.unitpricecalculator.shared.order.UnitSystemOrder
import com.sixbynine.unitpricecalculator.shared.preferences
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromHexString
import kotlinx.serialization.encodeToHexString
import kotlinx.serialization.protobuf.ProtoBuf

@OptIn(ExperimentalSerializationApi::class)
object UnitSystemOrderManager {

  private const val prefsKey = "unit_system_order"

  private val _current = nullableAtomicRef<UnitSystemOrder>().frozen()

  var current: UnitSystemOrder
    get() {
      _current.get()?.let { return it }
      val serialized = preferences.getString(prefsKey)
      val newValue = if (serialized == null) {
        UnitSystemOrder.default
      } else {
        ProtoBuf.decodeFromHexString(serialized)
      }
      _current.set(newValue.frozen())
      return newValue
    }
    set(value) {
      preferences.putString(prefsKey, ProtoBuf.encodeToHexString(value))
      _current.set(value.frozen())
    }

  internal fun resetForTesting() {
    _current.set(null)
  }

}