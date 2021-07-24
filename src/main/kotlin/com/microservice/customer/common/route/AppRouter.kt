package com.microservice.customer.common.route

import io.vertx.ext.web.Router

class AppRouter(val router: Router, routes: List<AppRoute>) {
  init {
    routes.forEach { it.addTo(router) }
  }
}
