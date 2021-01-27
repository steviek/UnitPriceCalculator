package com.sixbynine.unitpricecalculator.shared.ui

import com.sixbynine.unitpricecalculator.shared.comparison.Comparison

/** Data class representing the complete in-memory state of the app's UI. */
data class UiState(val comparisonTabState: ComparisonTabState)

data class ComparisonTabState(
  val comparison: Comparison
)