/*
 * MIT License
 *
 * Copyright (c) 2021 Pierre Adam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jackson42.play.jooq

import org.jooq.meta.jaxb
import org.jooq.meta.jaxb.{Generator, Jdbc, Target, Configuration => JooqConfiguration}
import play.api.Configuration
import play.api.db.Database

import javax.inject.Inject

/**
 * JooqConfigurationHelper.
 *
 * @author Pierre Adam
 * @since 21.07.06
 */
class JooqConfigurationHelper @Inject()(database: Database, config: Configuration) {

  def forgeConfiguration(dbName: String): JooqConfiguration = {
    new JooqConfiguration()
      .withJdbc(this.getJdbc(dbName))
      .withGenerator(this.getGenerator(dbName))
  }

  def getJdbc(dbName: String): Jdbc = {
    val jdbc = new Jdbc()
      .withDriver(this.config.get[String]("db.%s.driver".format(dbName)))
      .withUrl(this.config.get[String]("db.%s.url".format(dbName)))
      .withUser(this.config.get[String]("db.%s.username".format(dbName)))

    this.config.getOptional[String]("db.%s.password".format(dbName))
      .map(password => jdbc.withPassword(password))
    this.config.getOptional[Boolean]("db.%s.autocommit".format(dbName))
      .map(autoCommit => jdbc.withAutoCommit(autoCommit))

    jdbc
  }

  def getGenerator(dbName: String): Generator = {
    new Generator()
      .withDatabase(this.getDatabase(dbName))
      .withTarget(this.getTarget(dbName))
  }

  def getDatabase(dbName: String): jaxb.Database = {
    val jaxbDb = new jaxb.Database()
      .withName(this.config.get[String]("jooq.%s.database.type".format(dbName)))
      .withIncludes(this.config.getOptional[String]("jooq.%s.database.includes".format(dbName)).getOrElse(".*"))
      .withExcludes(this.config.getOptional[String]("jooq.%s.database.excludes".format(dbName)).getOrElse("play_evolutions|play_evolutions_lock"))

    this.config.getOptional[String]("jooq.%s.database.inputSchema".format(dbName)).map(inputSchema => jaxbDb.withInputSchema(inputSchema))
    jaxbDb
  }

  def getTarget(dbName: String): Target = {
    new Target()
      .withDirectory(this.config.get[String]("jooq.%s.target.directory".format(dbName)))
      .withPackageName(this.config.get[String]("jooq.%s.target.package".format(dbName)))
  }
}
