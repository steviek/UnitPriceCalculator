package com.sixbynine.unitpricecalculator.shared.comparison

import com.sixbynine.unitpricecalculator.shared.time.toDate
import platform.Foundation.*

fun Comparison.getReadableLastUpdatedDate(): String {
  val formatter = NSDateFormatter()
  formatter.dateStyle = NSDateFormatterShortStyle
  formatter.timeStyle = NSDateFormatterNoStyle
  formatter.locale = NSLocale.currentLocale
  return formatter.stringFromDate(lastUpdatedAt.toDate())
}