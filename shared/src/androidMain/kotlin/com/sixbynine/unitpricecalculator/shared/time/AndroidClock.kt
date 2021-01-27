package com.sixbynine.unitpricecalculator.shared.time

import android.os.SystemClock

actual val systemClock = object: Clock {
  override fun now(): Instant {
    return Instant(System.currentTimeMillis())
  }

  override fun uptime(): Duration {
    return SystemClock.elapsedRealtimeNanos().nanoseconds
  }
}