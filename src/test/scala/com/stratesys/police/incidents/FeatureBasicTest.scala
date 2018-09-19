package com.stratesys.police.incidents
import com.stratesys.police.incidents.commons.UtilsTest
import org.apache.spark.sql.{SQLContext, SparkSession}
import org.scalatest._


class FeatureBasicTest extends FeatureSpec with GivenWhenThen with Matchers with BeforeAndAfterAll with BeforeAndAfterEach {

  var utils: Option[UtilsTest] = None: Option[UtilsTest]

  override def beforeEach() : Unit = {
    super.beforeEach()
    sys.props("testing") = "true"
    System.setProperty("spark.master", "local")
    utils = Option(new UtilsTest)
  }

  override def afterEach(): Unit = {
    super.afterEach()
    utils.get.spark.sparkContext.stop()
  }

}