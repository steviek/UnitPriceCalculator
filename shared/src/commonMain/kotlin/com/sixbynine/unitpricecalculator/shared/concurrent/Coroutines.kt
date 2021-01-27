package com.sixbynine.unitpricecalculator.shared.concurrent

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

expect object Dispatchers {
  val main: CoroutineDispatcher
}

expect object Scopes {
  val main: CoroutineScope
}