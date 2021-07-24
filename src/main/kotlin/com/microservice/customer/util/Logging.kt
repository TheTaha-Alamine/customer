package com.microservice.customer.util

import io.vertx.core.impl.logging.LoggerFactory

fun loggerFor(java: Class<out Any>) = LoggerFactory.getLogger(java)!!
