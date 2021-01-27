package com.sixbynine.unitpricecalculator.shared.concurrent

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope

actual object Dispatchers {
  actual val main: CoroutineDispatcher = kotlinx.coroutines.Dispatchers.Main
}

actual object Scopes {
  actual val main: CoroutineScope = GlobalScope
}
