import com.google.common.truth.Truth.assertThat
import com.sixbynine.unitpricecalculator.shared.parseDouble
import com.sixbynine.unitpricecalculator.shared.unit.MeasureUnit
import com.sixbynine.unitpricecalculator.shared.unit.MeasureUnit.*
import com.sixbynine.unitpricecalculator.shared.unit.UnitEntry
import com.sixbynine.unitpricecalculator.shared.unit.UnitSystem
import com.sixbynine.unitpricecalculator.shared.unit.UnitType
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import org.junit.Test

@ExperimentalSerializationApi
class UnitEntryTest {

  private val onePer100G = UnitEntry(
    unit = GRAM,
    rawPrice = "1",
    rawSize = "100"
  )

  private val eight50Per6X330L = UnitEntry(
    unit = MILLILITRE,
    rawPrice = "8.50",
    rawQuantity = "6",
    rawSize = "330"
  )

  @Test
  fun serialization() {
    val encoded = ProtoBuf.encodeToByteArray(onePer100G)

    val decoded = ProtoBuf.decodeFromByteArray<UnitEntry>(encoded)

    assertThat(decoded).isEqualTo(onePer100G)
  }

  @Test
  fun getPricePer_weight() {
    assertThat(onePer100G.getPricePer(1.0, KILOGRAM)).isEqualTo(10)
    assertThat(onePer100G.getPricePer(100.0, GRAM)).isEqualTo(1)
    assertThat(onePer100G.getPricePer(1.0, GRAM)).isEqualTo(0.01)
  }

  @Test
  fun getPricePer_volume() {
    assertThat(eight50Per6X330L.getPricePer(1.0, LITRE)).isIn(4.2..4.3)
    assertThat(eight50Per6X330L.getPricePer(1.0, GALLON)).isIn(19.5..19.6)
    assertThat(eight50Per6X330L.getPricePer(330.0, MILLILITRE)).isIn(1.4..1.42)
  }

  @Test
  fun getPricePer_noPrice() {
    assertThat(UnitEntry(unit = FLUID_OUNCE).getPricePer(1.0, LITRE)).isNull()
  }

  @Test
  fun getPricePer_free() {
    assertThat(UnitEntry(unit = FLUID_OUNCE, rawPrice = "0").getPricePer(1.0, LITRE)).isZero()
  }

  @Test
  fun parseDoubleOrNull() {
    assertThat("2,3".parseDouble()).isEqualTo(2.3)
    assertThat("2.3".parseDouble()).isEqualTo(2.3)
    assertThat("2".parseDouble()).isEqualTo(2.0)
  }

  @Test
  fun uniqueIds() {
    val measureUnitIds = mutableSetOf<Int>()
    MeasureUnit.values().forEach {
      check(measureUnitIds.add(it.id)) { "Duplicate unit id: ${it.id}"}
    }

    val unitTypeIds = mutableSetOf<Int>()
    UnitType.values().forEach {
      check(unitTypeIds.add(it.id)) { "Duplicate unit type id: ${it.id}"}
    }

    val unitSystemIds = mutableSetOf<Int>()
    UnitSystem.values().forEach {
      check(unitSystemIds.add(it.id)) { "Duplicate unit system id: ${it.id}"}
    }
  }
}