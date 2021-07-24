package com.microservice.customer.common.route

import io.vertx.ext.web.RoutingContext

class LiveHandler {

  val handle: (RoutingContext) -> Unit = { context ->
    context.response().end("OK")
  }
}
