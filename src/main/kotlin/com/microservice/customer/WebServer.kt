package com.microservice.customer

import com.microservice.customer.common.route.AppRouter
import com.microservice.customer.config.Env
import com.microservice.customer.util.loggerFor
import com.uchuhimo.konf.Config
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.net.PemKeyCertOptions
import io.vertx.kotlin.core.http.listenAwait

class WebServer(private val vertx: Vertx, private val config: Config, private val router: AppRouter) {
  private val log = loggerFor(this::class.java)

  suspend fun start(): HttpServer {
    log.info("Starting server...")
    val serverOptions = HttpServerOptions()
      .setCompressionSupported(true)
      .setSsl(true)
    if (!config[Env.Http.ssl]) {
      serverOptions.isSsl = false
    } else {
      serverOptions.pemKeyCertOptions = PemKeyCertOptions().setKeyPath("key.pem").setCertPath("cert.pem")
    }
    val server = vertx.createHttpServer(serverOptions)
      .requestHandler(router.router)
      .listenAwait(config[Env.Http.port])
    log.info("Server started on ${server.actualPort()}")
    return server
  }
}
