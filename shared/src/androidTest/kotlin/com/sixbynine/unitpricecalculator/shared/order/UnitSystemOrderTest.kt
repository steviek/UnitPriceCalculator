package com.sixbynine.unitpricecalculator.shared.order

import com.google.common.truth.Truth.assertThat
import com.sixbynine.unitpricecalculator.shared.unit.UnitSystem
import org.junit.Test

class UnitSystemOrderTest {

  @Test
  fun default_value() {
    val unitSystemOrder = UnitSystemOrder.default

    assertThat(unitSystemOrder.pairs.map { it.system }).containsExactly(
      UnitSystem.METRIC,
      UnitSystem.IMPERIAL_UK,
      UnitSystem.IMPERIAL_US
    )
  }

  @Test
  fun swapped() {
    val unitSystemOrder = UnitSystemOrder.default

    val swapped = unitSystemOrder.withMovedIndex(2, 0)

    assertThat(swapped.pairs.map { it.system }).containsExactly(
      UnitSystem.IMPERIAL_US,
      UnitSystem.METRIC,
      UnitSystem.IMPERIAL_UK
    )
  }
}