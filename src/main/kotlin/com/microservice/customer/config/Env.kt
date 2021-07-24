package com.microservice.customer.config

import com.uchuhimo.konf.ConfigSpec

object Env : ConfigSpec() {

  object Http : ConfigSpec() {
    val port by optional(name = "port", description = "Port of the application", default = 8080)
    val ssl by optional(name = "ssl", description = "Server runs in SSL mode, serving HTTPS", default = false)
    val proxy by optional<String?>(name = "proxy", description = "Proxy used by application", default = null)
  }

  object Webclient : ConfigSpec() {
    val connectTimeoutInMs by optional(5000)
    val idleTimeoutInSeconds by optional(20)

    val enableSsl by optional(
      name = "ssl",
      description = "Enable SSL in webclients",
      default = true
    )
  }
}
