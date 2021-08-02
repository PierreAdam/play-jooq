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

package com.jackson42.play.jooq.generator

import com.jackson42.play.jooq.configuration.{JooqConfiguration, JooqGeneratorConfig}
import org.jooq.meta.jaxb
import org.jooq.meta.jaxb.{Configuration, Generator, Jdbc, Target}
import play.api.db.Database

import javax.inject.Inject

/**
 * JooqGenerationHelper.
 *
 * @author Pierre Adam
 * @since 21.07.06
 */
class JooqGenerationHelperImpl @Inject()(jooqConfiguration: JooqConfiguration) extends JooqGenerationHelper {

  override def getConfigurationForDb(dbName: String): JooqGeneratorConfig = jooqConfiguration.getGeneratorConfig(dbName)

  override def getJdbc(config: JooqGeneratorConfig): Jdbc = {
    val jdbc = new Jdbc()
      .withDriver(config.jdbcDriver)
      .withUrl(config.jdbcUrl)
      .withUser(config.jdbcUsername)

    config.jdbcPassword.map(password => jdbc.withPassword(password))
    config.jdbcAutoCommit.map(autoCommit => jdbc.withAutoCommit(autoCommit))

    jdbc
  }

  override def getDatabase(config: JooqGeneratorConfig): jaxb.Database = {
    val jaxbDb = new jaxb.Database()
      .withName(config.dbType)
      .withIncludes(config.dbIncludes)
      .withExcludes(config.dbExcludes)

    config.dbSchema.map(inputSchema => jaxbDb.withInputSchema(inputSchema))

    jaxbDb
  }

  override def getTarget(config: JooqGeneratorConfig): Target = new Target()
    .withDirectory(config.generatorDirectory)
    .withPackageName(config.generatorPackage)
}
