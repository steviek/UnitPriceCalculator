package com.sixbynine.unitpricecalculator.shared.concurrent

expect class AtomicRef<T>(value: T) {

  fun set(value: T)

  fun get(): T
}

fun <T: Any> nullableAtomicRef(): AtomicRef<T?> {
  return AtomicRef(null)
}

expect fun <T> T.frozen(): T