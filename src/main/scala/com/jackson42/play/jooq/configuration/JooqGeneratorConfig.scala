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
