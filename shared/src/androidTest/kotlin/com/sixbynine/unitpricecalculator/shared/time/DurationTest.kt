package com.sixbynine.unitpricecalculator.shared.time

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DurationTest {

  @Test
  fun millis_toString() {
    assertThat(200.milliseconds.toString()).isEqualTo("PT0.2S")
  }

  @Test
  fun seconds_toString() {
    assertThat(2.seconds.toString()).isEqualTo("PT2S")
  }

  @Test
  fun hours_toString() {
    assertThat(Duration.ofHours(1.5).toString()).isEqualTo("PT1H30M")
  }
}