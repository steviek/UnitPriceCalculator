package com.sixbynine.unitpricecalculator.shared.comparison.ui

import kotlin.math.roundToInt

data class ComparisonUiDimensions(
  val indexWidth: Int,
  val priceWidth: Int,
  val quantityWidth: Int,
  val sizeWidth: Int,
  val unitWidth: Int
) {

  companion object {
    fun forScreenWidth(width: Int): ComparisonUiDimensions {
      val indexWidth = width / 12
      val priceWidth = (width * 0.25).roundToInt()
      val sizeWidth = (width * 0.25).roundToInt()
      val quantityWidth = width / 7
      val unitWidth = width - indexWidth - priceWidth - sizeWidth - quantityWidth
      return ComparisonUiDimensions(
        indexWidth = indexWidth,
        priceWidth = priceWidth,
        quantityWidth = quantityWidth,
        sizeWidth = sizeWidth,
        unitWidth = unitWidth
      )
    }
  }
}