package com.jackson42.play.jooq.seeds

import com.jackson42.play.jooq.configuration.JooqConfiguration

import javax.inject.Inject

/**
 * JooqSeeds.
 *
 * @author Pierre Adam
 * @since 21.07.30
 */
class JooqSeeds @Inject()(jooqConfiguration: JooqConfiguration) {
  jooqConfiguration.getDbWithSeeds.foreach((dbName: String) => {
    // TODO
  })
}
