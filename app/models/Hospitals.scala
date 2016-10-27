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
  //TODO Implicit Write Object
  implicit object HospitalWrites extends OWrites[Hospital] {
    def writes(hospital: Hospital): JsObject = Json.obj(
      "name" -> hospital.name,
      "email" -> hospital.email,
      "mobile" -> hospital.mobile,
      "address" -> hospital.address,
      "zip" -> hospital.zipcode,
      "state" -> hospital.state,
      "website" -> hospital.website,
      "hospital_type" -> hospital.hostype,
      "discription" -> hospital.discription
    )
  }
  //TODO Implicit Read Object
  implicit object HospitalReads extends Reads[Hospital] {
    def reads(json: JsValue): JsResult[Hospital] = json match {
      case obj: JsObject => try {
        val name = (obj \ "name").as[String]
        val email = (obj \ "email").as[String]
        val phone = (obj \ "mobile").as[ListBuffer[Mobile]]
        val address = (obj \ "address").as[String]
        val zipcode = (obj \ "zip").as[String]
        val state = (obj \ "state").as[String]
        val website = (obj \ "website").as[String]
        val hostype = (obj \ "hospital_type").as[String]
        val discription = (obj \ "discription").as[String]
        JsSuccess(Hospital(name, email, phone, address, zipcode, state, website, hostype, discription))
      } catch {
        case cause: Throwable => JsError(cause.getMessage)
      }
      case _ => JsError("expected.jsobject")
    }
  }
}

