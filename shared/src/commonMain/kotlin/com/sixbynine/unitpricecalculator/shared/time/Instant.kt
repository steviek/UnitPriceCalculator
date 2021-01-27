package com.sixbynine.unitpricecalculator.shared.time

import kotlinx.serialization.Serializable

@Serializable
data class Instant(val epochMillis: Long) : Comparable<Instant> {

  val epochSeconds: Long
    get() = epochMillis / 1000

  operator fun plus(duration: Duration): Instant {
    return Instant(epochMillis + duration.toMillis())
  }

  operator fun minus(duration: Duration): Instant {
    return Instant(epochMillis - duration.toMillis())
  }

  companion object {
    val epoch = Instant(0)
  }

  override fun compareTo(other: Instant): Int {
    return epochMillis.compareTo(other.epochMillis)
  }
}