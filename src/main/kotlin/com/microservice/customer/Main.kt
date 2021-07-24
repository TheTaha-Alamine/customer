package com.microservice.customer

import kotlinx.coroutines.runBlocking
import org.kodein.di.generic.instance

class Main {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      runBlocking {
        val server: WebServer = Modules.binding.instance()
        server.start()
      }
    }
  }
}
