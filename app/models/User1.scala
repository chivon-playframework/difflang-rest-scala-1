package com.difflang.models

import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID
import play.modules.reactivemongo.json._

/**
 * Created by acer on 10/11/2016.
 */
case class User1(
  id: Option[BSONObjectID],
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
  implicit val user1Format = Json.format[User1]
}

