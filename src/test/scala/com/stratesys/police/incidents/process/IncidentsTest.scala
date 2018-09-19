package com.stratesys.police.incidents.process

import com.stratesys.police.incidents.commons.ModelsClass._
import com.stratesys.police.incidents.FeatureBasicTest
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.catalyst.ScalaReflection
import org.apache.spark.sql.types.StructType

import org.junit.Assert
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
  * Created by franr on 17/09/2018.
  */
@RunWith(classOf[JUnitRunner])
class IncidentsTest extends FeatureBasicTest {
  feature("Test the process incidents") {

    scenario("Normal Process") {
      Given("1 valid tables from police_department: incidents")

      val ut = utils.get

      ut.spark.read
        .format("com.databricks.spark.csv")
        .option("header", "false")
        .option("delimiter", "|")
        .option("inferSchema", "false")
        .schema(ScalaReflection.schemaFor[IncidentsTable].dataType.asInstanceOf[StructType]).load("src/test/resources/INCIDENTS")
        .createOrReplaceTempView("incidents")

      val process = new Incidents(ut)

      When("Try to execute main Function")
      process.ProcessIncidentsTotal()

      Then("The Result is table incidents_by_category_year_month loaded")

      val data: DataFrame = utils.get.spark.read.table("incidents_by_category_year_month")

      Assert.assertEquals("we should have 20 rows in incidents_by_category_year_month", 20, data.count)
    }
  }
}
