package com.microservice.customer.common.route

import io.vertx.ext.web.Router

interface AppRoute {
  fun addTo(router: Router): Router
}
