package com.jackson42.play.jooq.configuration

import play.api.Configuration

import scala.jdk.CollectionConverters._

/**
 * JooqConfigurationScalaVersion.
 *
 * @author Pierre Adam
 * @since 21.08.02
 */
class JooqConfigurationScalaVersion {
  def getDbNamesFromConfig(configuration: Configuration): Seq[String] = configuration.underlying.getConfig("db").root().keySet().asScala.toSeq
}
