package com.sixbynine.unitpricecalculator.shared.order

import com.sixbynine.unitpricecalculator.shared.locale.currentLocale
import com.sixbynine.unitpricecalculator.shared.unit.MeasureUnit
import com.sixbynine.unitpricecalculator.shared.unit.UnitSystem
import kotlinx.serialization.Serializable

@Serializable
class UnitSystemOrder private constructor(val pairs: List<SystemOrderPair>) {

  operator fun contains(unit: MeasureUnit): Boolean {
    return pairs.any { it.isEnabled && it.system in unit.systems }
  }

  fun withSwappedIndices(first: Int, second: Int): UnitSystemOrder {
    val newPairs = mutableListOf<SystemOrderPair>()
    pairs.indices.forEach { index ->
      newPairs.add(
        when (index) {
          first -> pairs[second]
          second -> pairs[first]
          else -> pairs[index]
        }
      )
    }
    return UnitSystemOrder(newPairs)
  }

  fun withMovedIndex(previousIndex: Int, newIndex: Int): UnitSystemOrder {
    val newPairs = pairs.toMutableList()
    newPairs.add(newIndex, newPairs.removeAt(previousIndex))
    return UnitSystemOrder(newPairs)
  }

  fun withSystemToggled(system: UnitSystem): UnitSystemOrder {
    val newPairs = pairs.map {
      if (it.system == system) {
        it.copy(isEnabled = !it.isEnabled)
      } else {
        it.copy(isEnabled = it.isEnabled)
      }
    }
    return UnitSystemOrder(newPairs)
  }

  fun systemComparator() = Comparator<UnitSystem> { first, second ->
    pairs.indexOfFirst { it.system == first }.compareTo(pairs.indexOfFirst { it.system == second })
  }

  fun unitComparator() = Comparator<MeasureUnit> { first, second ->
    fun MeasureUnit.getLowestSystemIndex(): Int {
      var value = Int.MAX_VALUE
      pairs.forEachIndexed { index, pair ->
        if (pair.isEnabled && pair.system in systems) {
          value = minOf(index, value)
        }
      }
      return value
    }
    first.getLowestSystemIndex().compareTo(second.getLowestSystemIndex())
  }

  override fun equals(other: Any?): Boolean {
    if (other !is UnitSystemOrder) return false
    return pairs == other.pairs
  }

  override fun hashCode(): Int {
    return pairs.hashCode()
  }

  override fun toString(): String {
    return "UnitSystemOrder(pairs=$pairs)"
  }

  companion object {
    val default: UnitSystemOrder
      get() {
        val order = if (currentLocale().country.startsWith("US", ignoreCase = false)) {
          listOf(UnitSystem.IMPERIAL_US, UnitSystem.METRIC, UnitSystem.IMPERIAL_UK)
        } else {
          listOf(UnitSystem.METRIC, UnitSystem.IMPERIAL_UK, UnitSystem.IMPERIAL_US)
        }
        return UnitSystemOrder(order.map { SystemOrderPair(it, isEnabled = true) })
      }
  }
}

@Serializable
data class SystemOrderPair(val system: UnitSystem, val isEnabled: Boolean)