package com.stratesys.police.incidents.commons

import org.apache.spark.sql._
import scala.language.postfixOps

class UtilsIncidents {

  val spark: SparkSession = (sys.props.get("testing")) match {
    case (Some("true")) =>
      SparkSession.builder().getOrCreate()
    case _ =>
      val spark = SparkSession.builder().enableHiveSupport().getOrCreate()
      spark
  }
  val IncidentsDatabase: String = (sys.props.get("testing")) match {
    case (Some("true")) =>
      ""
    case _ =>
      "police_department."
  }

}