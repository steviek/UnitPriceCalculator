package com.sixbynine.unitpricecalculator.shared.concurrent

import kotlin.native.concurrent.AtomicReference
import kotlin.native.concurrent.freeze

actual class AtomicRef<T> actual constructor(value: T){

  private val reference = AtomicReference(value)

  actual fun set(value: T) {
    reference.compareAndSet(value, value)
  }

  actual fun get(): T {
    return reference.value
  }
}

actual fun <T> T.frozen(): T {
  return this.freeze()
}