package com.stratesys.police.incidents.process

import com.stratesys.police.incidents.commons.UtilsIncidents
import com.stratesys.police.incidents.commons.DataFrameUtils._

import org.apache.log4j.Logger
import org.apache.spark.sql.SaveMode
import org.apache.spark.sql.functions.{month, udf, year, concat, lit}

class Incidents(utils : UtilsIncidents) {

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

  /***
    * Calculate the percentage between two values
    */
  val calculatePercentage = udf((a: Double, b: Double) => {
    100 - (a / b * 100)
  })

  /***
    *
    *
    */
  def ProcessIncidentsTotal(): Unit = {
    val dfIncidents = utils.spark.read.table (utils.IncidentsDatabase + "incidents")

    val dfIncidentsYearMonth = dfIncidents
      .withColumn("Year", year($"date_".cast("date")))
      .withColumn("Month", month($"date_".cast("date")))

    val dfIncidntCategorys = dfIncidents
      .groupBy($"Category")
      .count().alias("num_incidnt_categorys")

    val dfIncidntCategorysYearMonth = dfIncidentsYearMonth
      .groupBy($"Year", $"Month",$"Category")
      .count()
      .orderBy($"Year",$"Month",$"Category")

    val dfIncidntCategorysYearMonthCompareLastYear = dfIncidntCategorysYearMonth.as("current")
      .join(dfIncidntCategorysYearMonth.withColumnRenamed("count",
        "num_incidnt_categorys_ly").as("last"),
        $"current.Year" - 1 === $"last.Year"
          && $"current.Month" === $"last.Month"
          && $"current.Category" === $"last.Category",
        "left_outer")
      .select($"current.Year"
        ,$"current.Month"
        , $"current.Category"
        ,$"current.count".as("num_incidnt_categorys")
        , $"last.num_incidnt_categorys_ly")
      .withColumn("percentage", calculatePercentage($"num_incidnt_categorys_ly", $"num_incidnt_categorys"))

    dfIncidntCategorysYearMonthCompareLastYear.writeToES("incidents_category","incidents_by_category_year_month")
  }

  /***
    *
    */
  def ProcessIncidentsArrestLocation(): Unit = {
    val dfIncidents = utils.spark.read.table (utils.IncidentsDatabase + "incidents")

    val dfIncidentsArrest = dfIncidents.filter($"resolution" === "ARREST, BOOKED")
      .withColumn("Year", year($"date_".cast("date")))
      .withColumn("Month", month($"date_".cast("date")))
      .withColumn("location", concat($"y",lit(","),$"x"))
      .select($"resolution", $"year", $"month", $"location")

    dfIncidentsArrest.writeToES("incidents_location","filter_arrest")


  }

}
