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
class JooqConfiguration @Inject()(configuration: Configuration) extends JooqConfigurationScalaVersion {

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
  def getDbWithSeeds: Seq[String] = this.getDbNamesFromConfig(this.configuration)
    .filter((dbName: String) => {
      this.configuration.getOptional[Boolean]("jooq.%s.seeds.enabled".format(dbName)).getOrElse(false)
    })
}
