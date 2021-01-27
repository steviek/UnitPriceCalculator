package com.sixbynine.unitpricecalculator.shared.manager

import com.google.common.truth.Truth.assertThat
import com.sixbynine.unitpricecalculator.shared.UnitPriceCalculatorApplication
import com.sixbynine.unitpricecalculator.shared.order.UnitSystemOrder
import com.sixbynine.unitpricecalculator.shared.preferences
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromHexString
import kotlinx.serialization.encodeToHexString
import kotlinx.serialization.protobuf.ProtoBuf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.*

@OptIn(ExperimentalSerializationApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(application = UnitPriceCalculatorApplication::class)
class UnitSystemOrderManagerTest {

  private val prefsKey = "unit_system_order"

  @Before
  fun setUp() {
    Locale.setDefault(Locale.UK)
    preferences.remove(prefsKey)
    UnitSystemOrderManager.resetForTesting()
  }

  @Test
  fun current_noneSet_shouldBeDefault() {
    assertThat(UnitSystemOrderManager.current).isEqualTo(UnitSystemOrder.default)
  }

  @Test
  fun current_valueStored_shouldUseStoredValue() {
    val newOrder = UnitSystemOrder.default.withSwappedIndices(0, 1)

    preferences.putString(prefsKey, ProtoBuf.encodeToHexString(newOrder))

    assertThat(UnitSystemOrderManager.current).isEqualTo(newOrder)
  }

  @Test
  fun current_setToNewValue_shouldBeNewValue() {
    val newOrder = UnitSystemOrderManager.current.withSwappedIndices(0, 1)

    UnitSystemOrderManager.current = newOrder

    assertThat(UnitSystemOrderManager.current).isEqualTo(newOrder)
  }

  @Test
  fun current_setToNewValue_shouldStoreValue() {
    val newOrder = UnitSystemOrderManager.current.withSwappedIndices(0, 1)

    UnitSystemOrderManager.current = newOrder

    assertThat(ProtoBuf.decodeFromHexString<UnitSystemOrder>(preferences.getString(prefsKey)!!))
      .isEqualTo(newOrder)
  }
}