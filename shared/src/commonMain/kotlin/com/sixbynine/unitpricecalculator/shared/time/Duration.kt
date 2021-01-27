package com.sixbynine.unitpricecalculator.shared.time

class Duration private constructor(private val nanoseconds: Long) {
  private constructor(nanoseconds: Double): this(nanoseconds.toLong())

  fun toMillis(): Long {
    return nanoseconds / NANOS_IN_MILLISECOND
  }

  fun toSeconds(): Long {
    return nanoseconds / NANOS_IN_SECOND
  }

  operator fun minus(other: Duration): Duration {
    return Duration(nanoseconds - other.nanoseconds)
  }

  operator fun plus(other: Duration): Duration {
    return Duration(nanoseconds + other.nanoseconds)
  }

  override fun toString(): String {
    val durationAsSeconds = nanoseconds.toDouble() / NANOS_IN_SECOND
    val seconds = durationAsSeconds % SECONDS_IN_MINUTE
    val secondsString = when (seconds) {
      0.0 -> ""
      seconds.toInt().toDouble() -> "${seconds.toInt()}S"
      else -> "${seconds}S"
    }

    val durationAsMinutes = durationAsSeconds.toLong() / SECONDS_IN_MINUTE
    val minutes = durationAsMinutes % MINUTES_IN_HOUR
    val hours = durationAsMinutes / MINUTES_IN_HOUR
    return when {
      hours > 0 -> "PT${hours}H${minutes}M$secondsString"
      minutes > 0 -> "PT${minutes}M$secondsString"
      else -> "PT$secondsString"
    }
  }

  companion object {

    private const val NANOS_IN_MILLISECOND = 1_000_000
    private const val NANOS_IN_SECOND = 1_000_000_000
    private const val SECONDS_IN_MINUTE = 60
    private const val MINUTES_IN_HOUR = 60
    private const val SECONDS_IN_HOUR = SECONDS_IN_MINUTE * MINUTES_IN_HOUR

    val zero = Duration(0)

    fun ofHours(hours: Number) = ofSeconds(hours.toDouble() * SECONDS_IN_HOUR)

    fun ofMinutes(minutes: Number) = ofSeconds(minutes.toDouble() * SECONDS_IN_MINUTE)

    fun ofSeconds(seconds: Number) = Duration(seconds.toDouble() * NANOS_IN_SECOND)

    fun ofMillis(millis: Number) = Duration(millis.toDouble() * 1_000_000)

    fun ofMicros(micros: Number) = Duration(micros.toDouble() * 1_000)

    fun ofNanos(nanos: Number) = Duration(nanos.toLong())
  }
}

val Number.hours: Duration get() = Duration.ofHours(this)
val Number.minutes: Duration get() = Duration.ofMinutes(this)
val Number.seconds: Duration get() = Duration.ofSeconds(this)
val Number.milliseconds: Duration get() = Duration.ofMillis(this)
val Number.microsseconds: Duration get() = Duration.ofMicros(this)
val Number.nanoseconds: Duration get() = Duration.ofNanos(this)