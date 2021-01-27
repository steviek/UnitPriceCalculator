package com.sixbynine.unitpricecalculator.shared.manager

import com.sixbynine.unitpricecalculator.shared.concurrent.frozen
import com.sixbynine.unitpricecalculator.shared.concurrent.nullableAtomicRef
import com.sixbynine.unitpricecalculator.shared.preferences
import com.sixbynine.unitpricecalculator.shared.unit.UnitType

object UnitTypeManager {

  private const val prefsKey = "unit_type"

  private val _current = nullableAtomicRef<UnitType>().frozen()

  var current: UnitType
    get() {
      _current.get()?.let { return it }
      return (preferences.getInt(prefsKey)?.let { UnitType.fromId(it) } ?: UnitType.WEIGHT)
        .also { _current.set(it.frozen()) }
    }
    set(value) {
      preferences.putInt(prefsKey, value.id)
      _current.set(value.frozen())
    }
}