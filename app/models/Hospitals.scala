package models

import play.api.libs.json.{ JsObject, OWrites }
import play.api.libs.json._
import scala.collection.mutable.ListBuffer
import play.modules.reactivemongo.json._

/**
 * Created by CHHAI CHIVON on 10/21/2016.
 */

//TODO Use Case Class Mobile for to be BSON
case class Mobile(
  phone: String
)
//TODO CREATE BSONDocument FORMATE
object Mobile {
  //TODO Implicit Write and Read Json
  implicit val mobileFormat = Json.format[Mobile]

}
//TODO Use Case Class for to be BSON
case class Hospital(
  name: String,
  email: String,
  mobile: ListBuffer[Mobile], // TODO INJECT LIST OF PHONE NUMBER
  address: String,
  zipcode: String,
  state: String,
  website: String,
  hospital_type: String,
  discription: String
)
//TODO Making JSON FORMAT
object Hospital {

  //TODO Implicit Write AND Read Object
  implicit val hospialFormat = Json.format[Hospital]

}

