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

import javax.inject.Inject;
import java.util.function.Function;

/**
 * JavaJooq.
 *
 * @author Pierre Adam
 * @since 21.07.06
 */
public class JooqImpl implements Jooq {

    /**
     * The Internal JOOQ instance.
     */
    private final com.jackson42.play.jooq.scala.Jooq internal;

    /**
     * Instantiates a new Java jooq.
     *
     * @param jooq the jooq
     */
    @Inject
    public JooqImpl(final com.jackson42.play.jooq.scala.Jooq jooq) {
        this.internal = jooq;
    }

    @Override
    public <T> T openConnectionContext(final Database db, final Function<DSLContext, T> logic) {
        return this.internal.openConnectionContext(db.asScala(), logic::apply);
    }

    @Override
    public <T> T openConnectionContext(final Function<DSLContext, T> logic) {
        return this.internal.openConnectionContext(logic::apply);
    }

    @Override
    public DSLContext getContext(final Database db) {
        return this.internal.getContext(db.asScala());
    }

    @Override
    public DSLContext getContext() {
        return this.internal.getContext();
    }

    @Override
    public <T extends Record> T newRecord(final Database db, final Table<T> table) {
        return this.internal.newRecord(db.asScala(), table);
    }

    @Override
    public <T extends Record> T newRecord(final Table<T> table) {
        return this.internal.newRecord(table);
    }
}
