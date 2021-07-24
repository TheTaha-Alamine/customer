package com.microservice.customer.common.route

import io.vertx.ext.web.Router

class LiveRoute(private val liveHandler: LiveHandler) : AppRoute {

  companion object {
    const val livePath = "/live"
  }

  override fun addTo(router: Router): Router {
    router.get(livePath)
      .handler {
        liveHandler.handle(it)
      }
    return router
  }
}
