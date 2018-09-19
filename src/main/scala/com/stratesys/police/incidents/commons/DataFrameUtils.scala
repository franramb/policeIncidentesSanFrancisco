package com.stratesys.police.incidents.commons

import org.apache.spark.sql.{DataFrame}
import org.elasticsearch.spark.sql._

object DataFrameUtils extends UtilsIncidents {

  implicit class DataFrameMethods(df: DataFrame) {

    /**
      * Function that creates a ES table during execution or creates a temp view during testing
      *
      * @param tableName tableName
      */
    def writeToES(index: String, tableName: String): Unit = {
      sys.props.get("testing") match {
        case Some("true") => df.createOrReplaceTempView(tableName)
        case _ => df.saveToEs(index + "/" + tableName)
      }
    }
  }
}
