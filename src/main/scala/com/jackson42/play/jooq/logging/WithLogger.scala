package com.jackson42.play.jooq.logging

import org.slf4j.{Logger, LoggerFactory}

/**
 * WithLogger.
 *
 * @author Pierre Adam
 * @since 21.08.02
 */
trait WithLogger {
  val logger: Logger = LoggerFactory.getLogger(this.getClass)
}
