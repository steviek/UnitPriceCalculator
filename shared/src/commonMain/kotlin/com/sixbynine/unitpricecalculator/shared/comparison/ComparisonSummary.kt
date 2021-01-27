package com.sixbynine.unitpricecalculator.shared.comparison

data class ComparisonSummary(
  val summaryLine: String,
  val items: List<SummaryItem>
)

data class SummaryItem(val rowIndex: Int, val isSuperior: Boolean, val summary: String)