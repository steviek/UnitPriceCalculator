package com.sixbynine.unitpricecalculator.shared.time

import platform.Foundation.NSDate

private val timeIntervalBetween1970AndReferenceDate = 978307200.seconds

fun Instant.toDate(): NSDate {
  val durationSinceReferenceDate =
    this.epochMillis.milliseconds - timeIntervalBetween1970AndReferenceDate
  return NSDate(
    /* timeIntervalSinceReferenceDate= */ durationSinceReferenceDate.toSeconds().toDouble()
  )
}