package com.jackson42.play.jooq.configuration

import org.jooq.SQLDialect
import play.api.Configuration
import play.api.db.Database

import javax.inject.Inject

/**
 * JooqConfiguration.
 *
 * @author Pierre Adam
 * @since 21.07.30
 */
class JooqConfiguration @Inject()(configuration: Configuration) {

  /**
   * Gets the dialect from the configuration for the given database.
   *
   * @param db the database
   * @return the dialect
   */
  def dialect(db: Database): SQLDialect = {
    SQLDialect.valueOf(this.configuration.get[String]("jooq.%s.database.dialect".format(db.name)))
  }

  /**
   * Gets the necessary configuration for the generation of the models
   *
   * @param dbName the name of the database.
   * @return the configuration object
   */
  def getGeneratorConfig(dbName: String): JooqGeneratorConfig = new JooqGeneratorConfig(configuration, dbName)

  /**
   * Gets the databases with seeds configured.
   *
   * @return
   */
  def getDbWithSeeds: Seq[String] = new Configuration(this.configuration.underlying.atKey("db"))
    .keys
    .filter((dbName: String) => {
      this.configuration.getOptional[Boolean]("jooq.%s.seeds.enabled".format(dbName)).getOrElse(false)
    })
    .toSeq
}
