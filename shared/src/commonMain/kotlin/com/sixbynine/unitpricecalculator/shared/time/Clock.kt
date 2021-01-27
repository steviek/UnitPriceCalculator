package com.sixbynine.unitpricecalculator.shared.time

interface Clock {
  fun now(): Instant

  fun uptime(): Duration
}

expect val systemClock: Clock

fun now(): Instant {
  return systemClock.now()
}