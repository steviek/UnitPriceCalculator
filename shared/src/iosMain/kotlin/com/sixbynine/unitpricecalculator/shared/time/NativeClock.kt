package com.sixbynine.unitpricecalculator.shared.time

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970
import platform.QuartzCore.CACurrentMediaTime

actual val systemClock = object: Clock {
  override fun now(): Instant {
    return Instant((NSDate().timeIntervalSince1970 * 1000).toLong())
  }

  override fun uptime(): Duration {
    return CACurrentMediaTime().seconds
  }
}
