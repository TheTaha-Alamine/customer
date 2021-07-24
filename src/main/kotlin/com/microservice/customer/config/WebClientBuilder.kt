package com.microservice.customer.config

import com.uchuhimo.konf.Config
import io.vertx.core.Vertx
import io.vertx.core.net.ProxyOptions
import io.vertx.core.net.ProxyType
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.client.WebClientOptions
import java.net.URL

class WebClientBuilder(val vertx: Vertx, private val webclientConfig: Config) {

  private val enabledTlsVersions = setOf("TLSv1.2")

  fun build(): WebClient {
    val options = WebClientOptions()

    options.isSsl = webclientConfig[Env.Webclient.enableSsl]
    options.enabledSecureTransportProtocols = enabledTlsVersions
    options.isKeepAlive = false
    options.connectTimeout = webclientConfig[Env.Webclient.connectTimeoutInMs]
    options.idleTimeout = webclientConfig[Env.Webclient.idleTimeoutInSeconds]
    options.isTryUseCompression = true
    options.logActivity = true
    options.maxPoolSize = 50
    options.http2MaxPoolSize = 10

    val proxy = webclientConfig[Env.Http.proxy]
    if (!proxy.isNullOrBlank()) {
      val proxyUrl = URL(proxy)
      options.proxyOptions = ProxyOptions().setHost(proxyUrl.host).setPort(proxyUrl.port).setType(
        ProxyType.HTTP
      )
    }

    return WebClient.create(vertx, options)
  }
}
