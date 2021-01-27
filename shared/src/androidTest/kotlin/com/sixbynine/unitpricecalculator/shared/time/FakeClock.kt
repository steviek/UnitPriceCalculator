package com.sixbynine.unitpricecalculator.shared.time

class FakeClock : Clock {

  private var now = Instant.epoch
  private var uptime = Duration.zero

  override fun now(): Instant {
    return now
  }

  override fun uptime(): Duration {
    return uptime
  }

  fun setNow(now: Instant) {
    this.now = now
  }
}