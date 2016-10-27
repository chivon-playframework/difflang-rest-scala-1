package com.difflang.models

import play.api.libs.json._

/**
 * Created by acer on 10/11/2016.
 */
case class User1(
  first_name: String,
  last_name: String,
  password: String,
  email: String,
  address: String,
  country: String,
  state: String,
  city: String,
  zip: String,
  mobile: String,
  confirm_email: Boolean,
  active: Boolean
)
object User1 {

  //def insert(first_name:String,last_name:String,password:String,email:String,address:String,country:String,state:String,zip:String,phone:String)
  implicit object UserWrite extends OWrites[User1] {

    def writes(user: User1): JsObject = Json.obj(
      "first_name" -> user.first_name,
      "last_name" -> user.last_name,
      "password" -> user.password,
      "email" -> user.email,
      "address" -> user.address,
      "country" -> user.country,
      "state" -> user.state,
      "city" -> user.city,
      "zip" -> user.zip,
      "phone" -> user.mobile,
      "confirm_email" -> user.confirm_email,
      "active" -> user.active
    )
  }

  implicit object UserReads extends Reads[User1] {
    def reads(json: JsValue): JsResult[User1] = json match {
      case obj: JsObject =>
        try {
          val first_name = (obj \ "first_name").as[String]
          val last_name = (obj \ "last_name").as[String]
          val password = (obj \ "password").as[String]
          val email = (obj \ "email").as[String]
          val address = (obj \ "address").as[String]
          val country = (obj \ "country").as[String]
          val state = (obj \ "state").as[String]
          val city = (obj \ "city").as[String]
          val zip = (obj \ "zip").as[String]
          val mobile = (obj \ "mobile").as[String]
          val confirm_email = (obj \ "confirm_email").as[Boolean]
          val active = (obj \ "active").as[Boolean]

          JsSuccess(User1(first_name, last_name, password, email, address, country, state, city, zip, mobile, confirm_email, active))
        } catch {
          case cause: Throwable => JsError(cause.getMessage)
        }
      case _ => JsError("expected JsObject")
    }
  }
}

