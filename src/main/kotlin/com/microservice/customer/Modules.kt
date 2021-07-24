package com.microservice.customer

import com.microservice.customer.common.route.AppRouter
import com.microservice.customer.common.route.DefaultRoute
import com.microservice.customer.common.route.LiveHandler
import com.microservice.customer.common.route.LiveRoute
import com.microservice.customer.config.Env
import com.microservice.customer.config.WebClientBuilder
import com.uchuhimo.konf.Config
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.ext.web.Router
import io.vertx.ext.web.client.WebClient
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.ext.web.handler.ResponseContentTypeHandler
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.slf4j.MDCContext
import org.kodein.di.DKodein
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import java.net.CookieHandler
import kotlin.coroutines.CoroutineContext

object Modules {

  val mainModule = Kodein.Module("main") {
    bind<Vertx>() with singleton {
      Vertx.vertx(VertxOptions())
    }
    bind<WebServer>() with singleton {
      WebServer(
        vertx = instance(),
        config = instance(),
        router = instance()
      )
    }
    bind<Config>() with singleton {
      Config { addSpec(Env) }
        .from.systemProperties()
        .from.env()
    }
    bind<CoroutineContext>() with singleton { instance<Vertx>().dispatcher().plus(MDCContext()) }

    // Routing
    bind<Router>() with singleton { Router.router(instance()) }
    bind<AppRouter>() with singleton {
      AppRouter(
        router = instance(),
        routes = listOf(
          instance<DefaultRoute>(),
          instance<LiveRoute>()
        )
      )
    }
    bind<DefaultRoute>() with singleton {
      DefaultRoute(
        bodyHandler = instance(),
        //errorHandler = instance(),
        responseContentTypeHandler = instance()
      )
    }
    bind<LiveRoute>() with singleton {
      LiveRoute(
        liveHandler = instance()
      )
    }

    //handler
    bind<BodyHandler>() with singleton { BodyHandler.create() }
    bind<ResponseContentTypeHandler>() with singleton { ResponseContentTypeHandler.create() }
    bind<LiveHandler>() with singleton { LiveHandler() }

    // WebClient
    bind<WebClientBuilder>() with singleton {
      WebClientBuilder(
        vertx = instance(),
        webclientConfig = instance()
      )
    }
    bind<WebClient>() with provider {
      WebClientBuilder(
        vertx = instance(),
        webclientConfig = instance()
      ).build()
    }

  }
  val binding: DKodein = Kodein { import(mainModule) }.direct
}
