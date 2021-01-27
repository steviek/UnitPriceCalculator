package com.sixbynine.unitpricecalculator.shared.concurrent

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.Runnable
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.CoroutineContext

actual object Dispatchers {
  actual val main: CoroutineDispatcher = object : CoroutineDispatcher() {
      override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_main_queue()) {
          block.run()
        }
      }
    }
}

actual object Scopes {
  actual val main: CoroutineScope = object: CoroutineScope {

    private val dispatcher = Dispatchers.main
    private val job = Job()

    override val coroutineContext: CoroutineContext
      get() = dispatcher + job
  }
}
