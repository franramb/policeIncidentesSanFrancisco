package com.stratesys.police.incidents.process

import com.stratesys.police.incidents.commons.UtilsIncidents
import com.stratesys.police.incidents.commons.DataFrameUtils._
import org.apache.log4j.Logger
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.functions.{concat, lit, month, udf, year}

class Incidents(utils: UtilsIncidents) {

  val logger: Logger = Logger.getLogger(getClass.getName)

  import utils.spark.implicits._

  /**
    * Main Execution
    *
    */

  def mainExecution: Unit = {
    ProcessIncidentsTotal()
    ProcessIncidentsArrestLocation
  }

  /** *
    * Calculate the percentage between two values
    */
  val calculatePercentage = udf((a: Double, b: Double) => {
    100 - (a / b * 100)
  })

  /** *
    *
    *
    */
  def ProcessIncidentsTotal(): Unit = {

    //TODO

  }

  /** *
    *
    */
  def ProcessIncidentsArrestLocation(): Unit = {

    //TODO

  }

}
