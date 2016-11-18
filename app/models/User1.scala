package com.difflang.models

import org.joda.time.DateTime
import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID
import play.modules.reactivemongo.json._
/**
 * Created by Thanak on 10/11/2016.
 */
case class User1(
  _id: Option[BSONObjectID],
  firstname: String,
  lastname: String,
  email: String,
  confirm_email: Boolean,
  password: String,
  phone: String,
  created_date: Option[DateTime],
  active: Boolean,
  role: Byte
)

object User1 {
  implicit val user1Format = Json.format[User1]
}

