package com.sixbynine.unitpricecalculator.shared.concurrent

import java.util.concurrent.atomic.AtomicReference

actual class AtomicRef<T> actual constructor(value: T) {

  private val reference = AtomicReference(value)

  actual fun set(value: T) {
    reference.set(value)
  }

  actual fun get(): T {
    return reference.get()
  }
}

actual fun <T> T.frozen(): T = this