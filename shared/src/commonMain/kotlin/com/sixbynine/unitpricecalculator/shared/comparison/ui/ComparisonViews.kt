package com.sixbynine.unitpricecalculator.shared.comparison.ui

import com.sixbynine.unitpricecalculator.shared.manager.UnitSystemOrderManager
import com.sixbynine.unitpricecalculator.shared.unit.MeasureUnit
import com.sixbynine.unitpricecalculator.shared.unit.UnitType

object ComparisonViews {
  fun getUnitOptions(type: UnitType): List<MeasureUnit> {
    val order = UnitSystemOrderManager.current
    return MeasureUnit.values()
      .filter { it.type == type && it in order }
      .sortedWith(order.unitComparator())
  }
}