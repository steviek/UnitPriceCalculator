package com.sixbynine.unitpricecalculator.shared

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ResourcesTest {
  @Test
  fun plurals_equals() {
    assertThat(plurals("=1;;one;;other;;not one", quantity = 1)).isEqualTo("one")
  }

  @Test
  fun plurals_notEqual() {
    assertThat(plurals("=1;;one;;other;;not one", quantity = 2)).isEqualTo("not one")
  }

  @Test
  fun plurals_multipleNumbers() {
    assertThat(plurals("=1;;one;;=0;;zero;;other;;not one or zero", quantity = 0)).isEqualTo("zero")
    assertThat(plurals("=1;;one;;=0;;zero;;other;;not one or zero", quantity = 3))
      .isEqualTo("not one or zero")
  }

  @Test
  fun plurals_leq() {
    assertThat(plurals("<=1;;item;;other;;items", quantity = 0)).isEqualTo("item")
    assertThat(plurals("<=1;;item;;other;;items", quantity = 1)).isEqualTo("item")
    assertThat(plurals("<=1;;item;;other;;items", quantity = 2)).isEqualTo("items")
  }

  @Test
  fun plurals_lt() {
    assertThat(plurals("<2;;item;;other;;items", quantity = 0)).isEqualTo("item")
    assertThat(plurals("<2;;item;;other;;items", quantity = 1)).isEqualTo("item")
    assertThat(plurals("<2;;item;;other;;items", quantity = 2)).isEqualTo("items")
  }

  @Test
  fun plurals_geq() {
    assertThat(plurals(">=3;;lots;;other;;not lots", quantity = 3)).isEqualTo("lots")
    assertThat(plurals(">=3;;lots;;other;;not lots", quantity = 4)).isEqualTo("lots")
    assertThat(plurals(">=3;;lots;;other;;not lots", quantity = 2)).isEqualTo("not lots")
  }

  @Test
  fun plurals_gt() {
    assertThat(plurals(">3;;lots;;other;;not lots", quantity = 3)).isEqualTo("not lots")
    assertThat(plurals(">3;;lots;;other;;not lots", quantity = 4)).isEqualTo("lots")
    assertThat(plurals(">3;;lots;;other;;not lots", quantity = 2)).isEqualTo("not lots")
  }
}