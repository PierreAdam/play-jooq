package com.jackson42.play.jooq.configuration

import play.api.Configuration

/**
 * JooqGeneratorConfig.
 *
 * @author Pierre Adam
 * @since 21.07.30
 */
class JooqGeneratorConfig(configuration: Configuration, dbName: String) {
  val jdbcDriver: String = configuration.get[String]("db.%s.driver".format(dbName))
  val jdbcUrl: String = configuration.get[String]("db.%s.url".format(dbName))
  val jdbcUsername: String = configuration.get[String]("db.%s.username".format(dbName))
  val jdbcPassword: Option[String] = configuration.getOptional[String]("db.%s.password".format(dbName))
  val jdbcAutoCommit: Option[Boolean] = configuration.getOptional[Boolean]("db.%s.autocommit".format(dbName))

  val dbType: String = configuration.get[String]("jooq.%s.database.type".format(dbName))
  val dbIncludes: String = configuration.getOptional[String]("jooq.%s.database.includes".format(dbName)).getOrElse(".*")
  val dbExcludes: String = configuration.getOptional[String]("jooq.%s.database.excludes".format(dbName)).getOrElse("play_evolutions|play_evolutions_lock")
  val dbSchema: Option[String] = configuration.getOptional[String]("jooq.%s.database.inputSchema".format(dbName))

  val generatorDirectory: String = configuration.get[String]("jooq.%s.target.directory".format(dbName))
  val generatorPackage: String = configuration.get[String]("jooq.%s.target.package".format(dbName))
}
