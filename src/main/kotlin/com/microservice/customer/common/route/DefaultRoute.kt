package com.microservice.customer.common.route

import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.ErrorHandler
import io.vertx.ext.web.handler.ResponseContentTypeHandler

class DefaultRoute(
  private val bodyHandler: BodyHandler,
  //private val errorHandler: ErrorHandler,
  private val responseContentTypeHandler: ResponseContentTypeHandler
) : AppRoute {

  override fun addTo(router: Router): Router {
    val defaultRoute = router.route()
    defaultRoute.handler(bodyHandler)
    //defaultRoute.failureHandler(errorHandler)
    defaultRoute.handler(responseContentTypeHandler)
    return router
  }
}
