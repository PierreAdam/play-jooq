package com.jackson42.play.jooq.generator

import com.jackson42.play.jooq.configuration.JooqGeneratorConfig
import org.jooq.meta.jaxb
import org.jooq.meta.jaxb.{Configuration, Generator, Jdbc, Target}

/**
 * JooqGenerationHelper.
 *
 * @author Pierre Adam
 * @since 21.08.02
 */
trait JooqGenerationHelper {
  def getConfigurationForDb(dbName: String): JooqGeneratorConfig

  def forgeConfiguration(dbName: String): Configuration = this.forgeConfiguration(this.getConfigurationForDb(dbName))

  def forgeConfiguration(config: JooqGeneratorConfig): Configuration = new Configuration()
    .withJdbc(this.getJdbc(config))
    .withGenerator(this.getGenerator(config))

  def getJdbc(config: JooqGeneratorConfig): Jdbc

  def getGenerator(config: JooqGeneratorConfig): Generator = new Generator()
    .withDatabase(this.getDatabase(config))
    .withTarget(this.getTarget(config))

  def getDatabase(config: JooqGeneratorConfig): jaxb.Database

  def getTarget(config: JooqGeneratorConfig): Target
}
