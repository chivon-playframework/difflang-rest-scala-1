package com.difflang.models

import play.api.libs.json._

/**
 * Created by acer on 10/11/2016.
 */
case class User1(
  Firstname: String,
  LastName: String,
  Email: String,
  Address: String,
  Country: String,
  State: String,
  City: String,
  ZipCode: String,
  Phone: String
)
object User1 {
  implicit object UserWrite extends OWrites[User1] {

    def writes(user: User1): JsObject = Json.obj(
      "FIRSTNAME" -> user.Firstname,
      "LASTNAME" -> user.LastName,
      "EMAIL" -> user.Email,
      "ADDRESS" -> user.Address,
      "COUNTRY" -> user.Country,
      "STATE" -> user.State,
      "CITY" -> user.City,
      "ZIPCODE" -> user.ZipCode,
      "PHONE" -> user.Phone
    )
  }

  implicit object UserReads extends Reads[User1] {
    def reads(json: JsValue): JsResult[User1] = json match {
      case obj: JsObject =>
        try {

          val FirstName = (obj \ "FIRSTNAME").as[String]
          val LastName = (obj \ "LASTNAME").as[String]
          val Email = (obj \ "EMAIL").as[String]
          val Address = (obj \ "ADDRESS").as[String]
          val Country = (obj \ "COUNTRY").as[String]
          val State = (obj \ "STATE").as[String]
          val City = (obj \ "CITY").as[String]
          val ZipCode = (obj \ "ZIPCODE").as[String]
          val Phone = (obj \ "PHONE").as[String]

          JsSuccess(User1(FirstName, LastName, Email, Address, Country, State, City, ZipCode, Phone))
        } catch {
          case cause: Throwable => JsError(cause.getMessage)
        }
      case _ => JsError("expected JsObject")
    }
  }
}

