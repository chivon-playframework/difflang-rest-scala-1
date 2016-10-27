package models

import play.api.libs.json.{ JsObject, OWrites }
import play.api.libs.json._
import scala.collection.mutable.ListBuffer

/**
 * Created by CHHAI CHIVON on 10/21/2016.
 */

//TODO Use Case Class Mobile for to be BSON
case class Mobile(
  phone: String
)
//TODO CREATE BSONDocument FORMATE
object Mobile {
  implicit object MobileWriter extends OWrites[Mobile] {
    def writes(mobile: Mobile): JsObject = Json.obj(
      "phone" -> mobile.phone
    )
  }
  implicit object MobileReade extends Reads[Mobile] {
    override def reads(json: JsValue): JsResult[Mobile] = json match {
      case obj: JsObject => try {
        val phone = (obj \ "phone").as[String]
        JsSuccess(Mobile(phone))
      } catch {
        case cause: Throwable => JsError(cause.getMessage)
      }
      case _ => JsError("expected.jsobject")
    }
  }
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
  hostype: String,
  discription: String
)
//TODO Making JSON FORMAT
object Hospital {
  implicit val hospitalFormat = Json.format[Hospital]
}

