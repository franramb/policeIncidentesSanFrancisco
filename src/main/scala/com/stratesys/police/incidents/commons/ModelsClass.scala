package com.stratesys.police.incidents.commons

object ModelsClass {
  case class IncidentsTable(
                        IncidntNum: String,
                        Category: String,
                        Descript: String,
                        DayOfWeek: String,
                        date_ : String,
                        Time: String,
                        PdDistrict: String,
                        Resolution: String,
                        Address: String,
                        X: Double,
                        Y: Double,
                        Location: String,
                        PdId: String)

}
