package com.sixbynine.unitpricecalculator.shared.comparison.ui

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ComparisonUiDimensionsTest {

  @Test
  fun sum_shouldNotExceedWidth() {
    val dimensions = ComparisonUiDimensions.forScreenWidth(288)
    assertThat(
      dimensions.indexWidth +
          dimensions.priceWidth +
          dimensions.quantityWidth +
          dimensions.sizeWidth +
          dimensions.unitWidth
    ).isAtMost(288)
  }

  @Test
  fun expectedValue() {
    val dimensions = ComparisonUiDimensions.forScreenWidth(288)
    assertThat(dimensions).isEqualTo(
      ComparisonUiDimensions(
        22,
        72,
        32,
        72,
        90
      )
    )
  }
}