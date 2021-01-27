package com.sixbynine.unitpricecalculator.shared.unit

import com.sixbynine.unitpricecalculator.shared.parseDouble
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class UnitEntry(
  val unit: MeasureUnit,
  val rawPrice: String = "",
  val rawQuantity: String = "",
  val rawSize: String = "",
  val note: String? = null
) {

  @Transient
  val price = rawPrice.parseDouble()

  @Transient
  val size = rawSize.parseDouble() ?: 1.0

  @Transient
  val quantity = rawQuantity.parseDouble() ?: 1.0

  fun isClearable(): Boolean {
    return rawPrice.isNotEmpty() ||
        rawQuantity.isNotEmpty() ||
        rawSize.isNotEmpty() ||
        !note.isNullOrEmpty()
  }

  fun cleared(): UnitEntry {
    return UnitEntry(unit)
  }

  fun getPricePer(size: Double, unit: MeasureUnit): Double? {
    if (price == null) return null
    if (unit.type != this.unit.type) {
      println("Cannot compare ${this.unit.type} to ${unit.type}")
      return null
    }
    val pricePerUnit = this.price / (this.quantity * this.size)
    val pricePerOtherUnit = pricePerUnit / (this.unit.factor / unit.factor)
    return size * pricePerOtherUnit
  }
}