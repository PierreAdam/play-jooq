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

package com.jackson42.play.jooq.scala

import org.jooq.impl.DSL
import org.jooq.{DSLContext, Record, SQLDialect, Table}
import play.api.Configuration
import play.api.db.Database

import java.sql.Connection
import javax.inject.Inject

/**
 * JooqImpl.
 *
 * @author Pierre Adam
 * @since 21.07.06
 */
class JooqImpl @Inject()(database: Database, configuration: Configuration) extends Jooq {

  /**
   * Initiate a query by creating a DSLContext using the given database.
   * A connection will be open and will only be closed when the logic finish.
   * See https://www.jooq.org/doc/3.1/manual/sql-building/dsl-context/connection-vs-datasource/
   *
   * @param db    the database that should be used for the connection
   * @param logic the logic utilizing the DSLContext
   * @tparam T the type of the return parameter
   * @return the value from the logic
   */
  override def openConnectionContext[T](db: Database, logic: DSLContext => T): T = {
    db.withConnection[T]((connection: Connection) => logic(DSL.using(connection)))
  }

  /**
   * Initiate a query by creating a DSLContext using the default database.
   * A connection will be open and will only be closed when the logic finish.
   * See https://www.jooq.org/doc/3.1/manual/sql-building/dsl-context/connection-vs-datasource/
   *
   * @param logic the logic utilizing the DSLContext
   * @tparam T the type of the return parameter
   * @return the value from the logic
   */
  override def openConnectionContext[T](logic: DSLContext => T): T =
    this.openConnectionContext(this.database, logic)

  /**
   * Get a DSLContext that is based on the DataSource configured inside of PlayFramework for the given database.
   * The connection will only be established when necessary and will be managed by PlayFramework.
   * See https://www.jooq.org/doc/3.1/manual/sql-building/dsl-context/connection-vs-datasource/
   *
   * @return the DSLContext from JOOQ
   */
  override def getContext(db: Database = database): DSLContext = {
    DSL.using(db.dataSource, this.dialectFromConfiguration(db))
  }

  /**
   * Get a DSLContext that is based on the DataSource configured inside of PlayFramework for the default database.
   * The connection will only be established when necessary and will be managed by PlayFramework.
   * See https://www.jooq.org/doc/3.1/manual/sql-building/dsl-context/connection-vs-datasource/
   *
   * @return the DSLContext from JOOQ
   */
  override def getContext: DSLContext =
    this.getContext(this.database)

  /**
   * Create a new record for the given table on the given database.
   *
   * @param db    the database that should be used for the connection
   * @param table the table of the record
   * @tparam T the type of the record
   * @return the new record
   */
  override def newRecord[T <: Record](db: Database, table: Table[T]): T =
    this.getContext(db).newRecord(table)

  /**
   * Create a new record for the given table on the default database.
   *
   * @param table the table of the record
   * @tparam T the type of the record
   * @return the new record
   */
  override def newRecord[T <: Record](table: Table[T]): T =
    this.getContext.newRecord(table)

  /**
   * Gets the dialect from the configuration for the given database.
   *
   * @param db the database
   * @return the dialect
   */
  private def dialectFromConfiguration(db: Database): SQLDialect = {
    SQLDialect.valueOf(this.configuration.get[String]("jooq.%s.database.dialect".format(db.name)))
  }
}
