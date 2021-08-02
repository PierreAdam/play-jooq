package com.jackson42.play.jooq.seeds

import com.jackson42.play.jooq.configuration.JooqConfiguration
import com.jackson42.play.jooq.logging.WithLogger

import javax.inject.Inject

/**
 * JooqSeeds.
 *
 * @author Pierre Adam
 * @since 21.07.30
 */
class JooqSeeds @Inject()(jooqConfiguration: JooqConfiguration) extends WithLogger {
  jooqConfiguration.getDbWithSeeds.foreach((dbName: String) => {
    this.logger.warn("DATABASE : {}", dbName);
  })
}
