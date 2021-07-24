package com.microservice.customer.util

import com.fasterxml.jackson.databind.MappingJsonFactory
import com.microservice.customer.Modules
import net.logstash.logback.decorate.JsonFactoryDecorator
import org.kodein.di.generic.instance

class JsonLogging : JsonFactoryDecorator {

  override fun decorate(factory: MappingJsonFactory): MappingJsonFactory = factory.apply {
    factory.codec = Modules.binding.instance()
  }
}
