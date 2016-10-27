package models

import reactivemongo.bson.BSONObjectID
//
/**
 * Created by Thanak on 10/11/2016.
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


