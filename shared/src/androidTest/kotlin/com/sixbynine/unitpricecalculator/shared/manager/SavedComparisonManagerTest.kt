package com.sixbynine.unitpricecalculator.shared.manager

import com.google.common.truth.Truth.assertThat
import com.sixbynine.unitpricecalculator.shared.UnitPriceCalculatorApplication
import com.sixbynine.unitpricecalculator.shared.comparison.Comparison
import com.sixbynine.unitpricecalculator.shared.time.FakeClock
import com.sixbynine.unitpricecalculator.shared.time.Instant
import com.sixbynine.unitpricecalculator.shared.unit.MeasureUnit.KILOGRAM
import com.sixbynine.unitpricecalculator.shared.unit.MeasureUnit.MILLILITRE
import com.sixbynine.unitpricecalculator.shared.unit.UnitEntry
import com.sixbynine.unitpricecalculator.shared.unit.UnitType.VOLUME
import com.sixbynine.unitpricecalculator.shared.unit.UnitType.WEIGHT
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(application = UnitPriceCalculatorApplication::class)
class SavedComparisonManagerTest {

  private val manager = SavedComparisonManager
  private val clock = FakeClock()

  private val unsavedComparison = Comparison(
    unitType = VOLUME,
    entries = listOf(UnitEntry(MILLILITRE)),
    comparisonUnit = MILLILITRE,
    createdAt = Instant.epoch,
    lastUpdatedAt = Instant.epoch
  )

  @Before
  fun setUp() {
    manager.setClockForTesting(clock)
  }

  @Test
  fun getSavedComparisons_default_shouldBeEmpty() {
    assertThat(manager.getSavedComparisons()).isEmpty()
  }

  @Test
  fun save_shouldAddIdAndTime() {
    clock.setNow(Instant(5))

    val savedComparison = manager.save(unsavedComparison)

    assertThat(savedComparison.id).isEqualTo(0)
    assertThat(savedComparison.createdAt).isEqualTo(Instant.epoch)
    assertThat(savedComparison.lastUpdatedAt).isEqualTo(Instant(5))
    assertThat(manager.getSavedComparisons()).containsExactly(savedComparison)
  }

  @Test
  fun save_alreadySaved() {
    clock.setNow(Instant(10))
    val savedComparison = manager.save(unsavedComparison)

    val savedComparisonTwice = manager.save(savedComparison)

    assertThat(savedComparisonTwice).isEqualTo(savedComparison)
    assertThat(manager.getSavedComparisons()).containsExactly(savedComparison)
  }

  @Test
  fun save_multipleComparisons() {
    val comparison0 = Comparison(
      unitType = WEIGHT,
      entries = listOf(UnitEntry(KILOGRAM)),
      comparisonUnit = KILOGRAM,
      createdAt = Instant(5),
      lastUpdatedAt = Instant(5)
    )

    val comparison1 = Comparison(
      unitType = VOLUME,
      entries = listOf(UnitEntry(MILLILITRE)),
      comparisonUnit = MILLILITRE,
      createdAt = Instant.epoch,
      lastUpdatedAt = Instant.epoch
    )

    val savedComparison0 = manager.save(comparison0)
    val savedComparison1 = manager.save(comparison1)

    assertThat(savedComparison0.id).isEqualTo(0)
    assertThat(savedComparison1.id).isEqualTo(1)
  }

  private companion object {
    const val prefsKey = "saved_data"
  }
}