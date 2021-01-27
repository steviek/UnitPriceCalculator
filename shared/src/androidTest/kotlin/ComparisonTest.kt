import com.google.common.truth.Truth.assertThat
import com.sixbynine.unitpricecalculator.shared.comparison.Comparison
import com.sixbynine.unitpricecalculator.shared.currency.Currency
import com.sixbynine.unitpricecalculator.shared.time.Instant
import com.sixbynine.unitpricecalculator.shared.unit.MeasureUnit.*
import com.sixbynine.unitpricecalculator.shared.unit.UnitEntry
import com.sixbynine.unitpricecalculator.shared.unit.UnitType.VOLUME
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import org.junit.Assert.assertThrows
import org.junit.Test

class ComparisonTest {

  val comparison = Comparison(
    unitType = VOLUME,
    entries = listOf(UnitEntry(LITRE, rawPrice = "1", rawSize = "10")),
    comparisonUnit = MILLILITRE,
    rawComparisonSize = "100",
    createdAt = Instant(1),
    lastUpdatedAt = Instant(1),
    currency = Currency("GBP")
  )

  @Test
  fun serialization() {
    val encoded = ProtoBuf.encodeToByteArray(comparison)

    val decoded = ProtoBuf.decodeFromByteArray<Comparison>(encoded)

    assertThat(decoded).isEqualTo(comparison)
  }

  @Test
  fun invalidUnits() {
    assertThrows(IllegalArgumentException::class.java) {
      Comparison(
        unitType = VOLUME,
        entries = listOf(UnitEntry(KILOGRAM)),
        comparisonUnit = MILLILITRE,
        createdAt = Instant(1),
        lastUpdatedAt = Instant(1),
        currency = Currency("GBP")
      )
    }

    assertThrows(IllegalArgumentException::class.java) {
      Comparison(
        unitType = VOLUME,
        entries = listOf(),
        comparisonUnit = MILLILITRE,
        createdAt = Instant(1),
        lastUpdatedAt = Instant(1),
        currency = Currency("GBP")
      )
    }
  }
}