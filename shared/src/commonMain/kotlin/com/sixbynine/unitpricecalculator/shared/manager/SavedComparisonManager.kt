package com.sixbynine.unitpricecalculator.shared.manager

import com.sixbynine.unitpricecalculator.shared.comparison.Comparison
import com.sixbynine.unitpricecalculator.shared.concurrent.frozen
import com.sixbynine.unitpricecalculator.shared.concurrent.nullableAtomicRef
import com.sixbynine.unitpricecalculator.shared.preferences
import com.sixbynine.unitpricecalculator.shared.time.Clock
import com.sixbynine.unitpricecalculator.shared.time.systemClock
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromHexString
import kotlinx.serialization.encodeToHexString
import kotlinx.serialization.protobuf.ProtoBuf

object SavedComparisonManager {

  private val testClock = nullableAtomicRef<Clock>().frozen()
  private val savedComparisons = nullableAtomicRef<SavedComparisonsData>().frozen()

  private const val prefsKey = "saved_data"

  private var savedData: SavedComparisonsData
    get() {
      savedComparisons.get()?.let { return it }
      val data = preferences.getString(prefsKey)?.let { encoded ->
        ProtoBuf.decodeFromHexString<SavedComparisonsData>(encoded)
      } ?: SavedComparisonsData()
      savedComparisons.set(data.frozen())
      return data
    }
    set(value) {
      preferences.putString(prefsKey, ProtoBuf.encodeToHexString(value))
      savedComparisons.set(value.frozen())
    }

  private val clock: Clock
    get() = testClock.get() ?: systemClock

  /** Saves the [comparison] to storage and returns a copy with the id and new modified time. */
  fun save(comparison: Comparison): Comparison {
    val newComparisons = savedData.comparisons.toMutableList()
    val comparisonId: Long
    val newLastId: Long

    if (comparison.id == null) {
      comparisonId = savedData.lastId + 1
      newLastId = comparisonId
    } else {
      newComparisons.removeAll { it.id == comparison.id }
      comparisonId = comparison.id
      newLastId = savedData.lastId
    }

    val updatedComparison = comparison.copy(
      id = comparisonId,
      lastUpdatedAt = clock.now()
    )
    newComparisons.add(updatedComparison)

    val updatedSavedData = SavedComparisonsData(
      newComparisons,
      newLastId
    )
    val encoded = ProtoBuf.encodeToHexString(updatedSavedData)
    preferences.putString(prefsKey, encoded)

    savedData = updatedSavedData

    return updatedComparison
  }

  fun delete(comparison: Comparison) {
    if (comparison.id == null) return
    savedData = savedData.copy(comparisons = savedData.comparisons - comparison)
  }

  fun getSavedComparisons(): List<Comparison> {
    return savedData.comparisons.sortedByDescending { it.lastUpdatedAt }
  }

  internal fun setClockForTesting(clock: Clock) {
    this.testClock.set(clock.frozen())
  }
}

@Serializable
private data class SavedComparisonsData(
  val comparisons: List<Comparison> = emptyList(),
  val lastId: Long = -1
)