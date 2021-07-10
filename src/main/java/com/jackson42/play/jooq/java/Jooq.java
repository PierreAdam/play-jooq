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

package com.jackson42.play.jooq.java;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Table;
import play.db.Database;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * JavaJooq.
 *
 * @author Pierre Adam
 * @since 21.07.06
 */
public interface Jooq {

    /**
     * Initiate a query by creating a DSLContext using the given database.
     *
     * @param <T>   the type of the return parameter
     * @param db    the database that should be used for the connection
     * @param logic the logic utilizing the DSLContext
     * @return the value from the logic
     */
    <T> T openConnectionContext(final Database db, final Function<DSLContext, T> logic);

    /**
     * Initiate a query by creating a DSLContext using the default database.
     *
     * @param <T>   the type of the return parameter
     * @param logic the logic utilizing the DSLContext
     * @return the value from the logic
     */
    <T> T openConnectionContext(final Function<DSLContext, T> logic);

    /**
     * Initiate a query by creating a DSLContext using the given database.
     *
     * @param db    the database that should be used for the connection
     * @param logic the logic utilizing the DSLContext
     */
    default void openConnectionContext(final Database db, final Consumer<DSLContext> logic) {
        this.openConnectionContext(db, dslContext -> {
            logic.accept(dslContext);
            return null;
        });
    }

    /**
     * Initiate a query by creating a DSLContext using the default database.
     *
     * @param logic the logic utilizing the DSLContext
     */
    default void openConnectionContext(final Consumer<DSLContext> logic) {
        this.openConnectionContext(dslContext -> {
            logic.accept(dslContext);
            return null;
        });
    }

    /**
     * Get a DSLContext that is based on the DataSource configured inside of PlayFramework for the given database.
     * The connection will only be established when necessary and will be managed by PlayFramework.
     * See https://www.jooq.org/doc/3.1/manual/sql-building/dsl-context/connection-vs-datasource/
     *
     * @return the DSLContext from JOOQ
     */
    DSLContext getContext(final Database db);

    /**
     * Get a DSLContext that is based on the DataSource configured inside of PlayFramework for the default database.
     * The connection will only be established when necessary and will be managed by PlayFramework.
     * See https://www.jooq.org/doc/3.1/manual/sql-building/dsl-context/connection-vs-datasource/
     *
     * @return the DSLContext from JOOQ
     */
    DSLContext getContext();

    /**
     * Create a new record for the given table on the given database.
     *
     * @param <T>   the type parameter
     * @param db    the db
     * @param table the table
     * @return the t
     */
    <T extends Record> T newRecord(final Database db, Table<T> table);

    /**
     * Create a new record for the given table on the default database.
     *
     * @param <T>   the type parameter
     * @param table the table
     * @return the t
     */
    <T extends Record> T newRecord(Table<T> table);
}
