
import com.stratesys.police.incidents.commons.UtilsIncidents
import com.stratesys.police.incidents.process.Incidents
import org.apache.log4j.{Level, Logger}


object IncidentsMain {

  val logger: Logger = Logger.getLogger(getClass.getName)

  def main(args: Array[String]): Unit = {

    //Suppress Spark output
    Logger.getLogger("akka").setLevel(Level.ERROR)
    val utils = new UtilsIncidents

    args match {
      case Array("-p") =>
        new Incidents(utils).mainExecution
      case _ =>
        logger.error("ERROR: Invalid option.")
        logger.error("IncidentsMain -p")
        System.exit(1)
    }


  }

}
