package com.sixbynine.unitpricecalculator.shared.time

class Stopwatch private constructor(private val clock: Clock) {

  private val baseTime = clock.uptime()

  fun elapsed(): Duration {
    return clock.uptime() - baseTime
  }

  companion object {
    fun createStarted(clock: Clock = systemClock): Stopwatch {
      return Stopwatch(clock)
    }
  }
}