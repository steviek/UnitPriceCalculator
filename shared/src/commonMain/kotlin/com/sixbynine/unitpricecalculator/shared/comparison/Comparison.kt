package com.sixbynine.unitpricecalculator.shared.comparison

import com.sixbynine.unitpricecalculator.shared.MR.strings
import com.sixbynine.unitpricecalculator.shared.currency.Currency
import com.sixbynine.unitpricecalculator.shared.currency.format
import com.sixbynine.unitpricecalculator.shared.load
import com.sixbynine.unitpricecalculator.shared.manager.CurrencyManager
import com.sixbynine.unitpricecalculator.shared.manager.UnitSystemOrderManager
import com.sixbynine.unitpricecalculator.shared.manager.UnitTypeManager
import com.sixbynine.unitpricecalculator.shared.parseDouble
import com.sixbynine.unitpricecalculator.shared.plurals
import com.sixbynine.unitpricecalculator.shared.time.Instant
import com.sixbynine.unitpricecalculator.shared.time.now
import com.sixbynine.unitpricecalculator.shared.unit.MeasureUnit
import com.sixbynine.unitpricecalculator.shared.unit.UnitEntry
import com.sixbynine.unitpricecalculator.shared.unit.UnitType
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.math.roundToInt

/**
 * Persistable data store of a unit price comparison.
 *
 * @property id the unique storage id of the comparison.
 * @property unitType the type of units being compared.
 * @property entries the list of entries in the comparison.
 * @property comparisonUnit the unit to use for comparison.
 * @property rawComparisonSize the value the user entered for comparison size.
 * @property name the displayable name for the comparison.
 * @property createdAt the time when the comparison was first created.
 * @property lastUpdatedAt the last time the comparison was saved.
 * @property currency the currency symbol associated with the comparison.
 */
@Serializable
data class Comparison(
  val id: Long? = null,
  val unitType: UnitType,
  val entries: List<UnitEntry>,
  val comparisonUnit: MeasureUnit,
  val rawComparisonSize: String = "",
  val name: String? = null,
  val createdAt: Instant,
  val lastUpdatedAt: Instant,
  val currency: Currency = Currency("USD")
) {

  init {
    require(entries.all { it.unit.type == unitType })
    require(comparisonUnit.type == unitType)
  }

  fun isClearable(): Boolean {
    return id != null ||
        entries.any { it.isClearable() } ||
        !name.isNullOrEmpty() ||
        rawComparisonSize.isNotEmpty()
  }

  fun isSaveable(lastSavedComparison: Comparison?): Boolean {
    return isClearable() && this != lastSavedComparison
  }

  @Transient
  val comparisonSize = rawComparisonSize.parseDouble() ?: comparisonUnit.defaultQuantity

  fun withUnitType(unitType: UnitType): Comparison {
    if (unitType == this.unitType) return this
    val unitSystemOrder = UnitSystemOrderManager.current
    val defaultUnit = MeasureUnit.values().sortedWith(unitSystemOrder.unitComparator()).first {
      it.type == unitType
    }
    return copy(
      unitType = unitType,
      entries = entries.map { it.copy(unit = defaultUnit) },
      comparisonUnit = defaultUnit,
    )
  }

  fun withCurrency(currency: Currency): Comparison {
    return copy(currency = currency)
  }

  fun withUnit(at: Int, unit: MeasureUnit): Comparison {
    if (entries[at].unit == unit) return this
    return copy(
      entries = entries.mapIndexed { index, unitEntry ->
        if (index == at) {
          unitEntry.copy(unit = unit)
        } else {
          unitEntry
        }
      }
    )
  }

  fun withRawPrice(at: Int, rawPrice: String): Comparison {
    if (entries[at].rawPrice == rawPrice) return this
    return copy(
      entries = entries.mapIndexed { index, unitEntry ->
        if (index == at) {
          unitEntry.copy(rawPrice = rawPrice)
        } else {
          unitEntry
        }
      }
    )
  }

  fun withRawQuantity(at: Int, rawQuantity: String): Comparison {
    if (entries[at].rawQuantity == rawQuantity) return this
    return copy(
      entries = entries.mapIndexed { index, unitEntry ->
        if (index == at) {
          unitEntry.copy(rawQuantity = rawQuantity)
        } else {
          unitEntry
        }
      }
    )
  }

  fun withRawSize(at: Int, rawSize: String): Comparison {
    if (entries[at].rawSize == rawSize) return this
    return copy(
      entries = entries.mapIndexed { index, unitEntry ->
        if (index == at) {
          unitEntry.copy(rawSize = rawSize)
        } else {
          unitEntry
        }
      }
    )
  }

  fun withNote(at: Int, note: String?): Comparison {
    if (entries[at].note == note) return this
    return copy(
      entries = entries.mapIndexed { index, unitEntry ->
        if (index == at) {
          unitEntry.copy(note = note)
        } else {
          unitEntry
        }
      }
    )
  }

  fun withComparisonSize(size: String): Comparison {
    return copy(rawComparisonSize = size)
  }

  fun withComparisonUnit(unit: MeasureUnit): Comparison {
    return copy(comparisonUnit = unit)
  }

  fun withRowAddedToEnd(): Comparison {
    return copy(entries = entries + UnitEntry(unit = comparisonUnit))
  }

  fun withRowRemovedFromEnd(): Comparison {
    return copy(entries = entries.dropLast(1))
  }

  /** Returns a summary for the entry row. */
  fun createSummaryString(entry: UnitEntry): String {
    return formatUnitPrice(entry.unitPrice ?: return "")
  }

  fun withName(name: String?): Comparison {
    return copy(name = name)
  }

  fun cleared(): Comparison {
    return copy(
      id = null,
      entries = entries.map { it.cleared() },
      rawComparisonSize = "",
      name = null,
      createdAt = now(),
      lastUpdatedAt = now()
    )
  }

  private fun formatEntryPrice(entry: UnitEntry): String {
    val price = entry.price ?: return ""
    val formattedPrice = currency.format(price) ?: return ""
    return when {
      entry.quantity == 1.0 && entry.size == 1.0 -> {
        StringDesc.ResourceFormatted(
          strings.m_per_u,
          formattedPrice,
          entry.unit.symbol.load()
        ).load()
      }
      entry.quantity == 1.0 -> {
        StringDesc.ResourceFormatted(
          strings.m_per_s_u,
          formattedPrice,
          entry.size.toPrettyString(),
          entry.unit.symbol.load()
        ).load()
      }
      entry.size == 1.0 -> {
        StringDesc.ResourceFormatted(
          strings.m_per_s_u,
          formattedPrice,
          entry.quantity.toPrettyString(),
          entry.unit.symbol.load()
        ).load()
      }
      else -> {
        StringDesc.ResourceFormatted(
          strings.m_per_qxs_u,
          formattedPrice,
          entry.quantity.toPrettyString(),
          entry.size.toPrettyString(),
          entry.unit.symbol.load()
        ).load()
      }
    }
  }

  private fun formatUnitPrice(
    price: Double
  ): String {
    val formattedUnitPrice = currency.format(price) ?: return ""
    return when (comparisonSize) {
      1.0 -> {
        StringDesc.ResourceFormatted(
          strings.m_per_u,
          formattedUnitPrice,
          comparisonUnit.symbol.load()
        ).load()
      }
      else -> {
        StringDesc.ResourceFormatted(
          strings.m_per_s_u,
          formattedUnitPrice,
          comparisonSize.toPrettyString(),
          comparisonUnit.symbol.load()
        ).load()
      }
    }
  }

  private fun Double.toPrettyString(): String {
    if (this.toInt().toDouble() == this) {
      return this.toInt().toString()
    } else {
      return this.toString()
    }
  }

  fun getStatus(entry: UnitEntry): ComparisonStatus {
    val unitPrices = entries.mapNotNull { it.getPricePer(comparisonSize, comparisonUnit) }
    if (unitPrices.size < 2) return ComparisonStatus.NONE
    val bestPrice = unitPrices.minOrNull() ?: return ComparisonStatus.NONE
    val unitPriceForEntry =
      entry.getPricePer(comparisonSize, comparisonUnit) ?: return ComparisonStatus.NONE
    return if (unitPriceForEntry <= bestPrice) {
      ComparisonStatus.SUPERIOR
    } else {
      ComparisonStatus.INFERIOR
    }
  }

  fun getSummary(): ComparisonSummary? {
    val sortedEntries = entries
      .mapNotNull { entry -> entry.unitPrice?.let { entry to it } }
      .withIndex()
      .toList()
      .sortedBy { it.value.second }

    val bestValue = sortedEntries.firstOrNull() ?: return null
    if (sortedEntries.size < 2) return null

    val summaryLine = StringDesc.ResourceFormatted(
      strings.main_final_summary,
      bestValue.index + 1
    ).load()

    val bestPrice = bestValue.value.second
    val summaryItems = sortedEntries.map { (index, entryAndUnitPrice) ->
      val (entry, unitPrice) = entryAndUnitPrice

      var summary = StringDesc.ResourceFormatted(
        strings.row_x,
        index + 1
      ).load() + "\n"

      summary += formatEntryPrice(entry) + " = " + formatUnitPrice(unitPrice)

      val isSuperior: Boolean
      if (unitPrice <= bestPrice) {
        isSuperior = true
      } else {
        val percentIncrease = (((unitPrice / bestPrice) - 1) * 100).roundToInt()
        summary += "\n(+${percentIncrease}%)"
        isSuperior = false
      }

      SummaryItem(index, isSuperior, summary)
    }

    return ComparisonSummary(
      summaryLine = summaryLine,
      items = summaryItems
    )
  }

  fun getSavedScreenTitle(): String {
    val formatted = StringDesc.ResourceFormatted(
      strings.pl_saved_comparison_items,
      name.let { if (it.isNullOrBlank()) strings.untitled.load() else it },
      entries.size
    ).load()
    return plurals(formatted, quantity = entries.size)
  }

  fun getSavedScreenSubtitle(): String {
    val bestItem = entries.minByOrNull { it.unitPrice ?: Double.MAX_VALUE }
    if (bestItem?.unitPrice == null) return " "
    return StringDesc.ResourceFormatted(
      strings.saved_comparison_subtitle,
      formatEntryPrice(bestItem)
    ).load()
  }

  /** The unit price of the entry for the current comparison, or null if it can't be computed. */
  private val UnitEntry.unitPrice: Double?
    get() = getPricePer(comparisonSize, comparisonUnit)

  companion object {
    fun create(): Comparison {
      val unitType = UnitTypeManager.current
      val unitSystemOrder = UnitSystemOrderManager.current
      val defaultUnit = MeasureUnit.values().sortedWith(unitSystemOrder.unitComparator()).first {
        it.type == unitType
      }
      return Comparison(
        unitType = unitType,
        entries = listOf(UnitEntry(defaultUnit), UnitEntry(defaultUnit)),
        comparisonUnit = defaultUnit,
        createdAt = now(),
        lastUpdatedAt = now(),
        currency = CurrencyManager.current
      )
    }
  }
}