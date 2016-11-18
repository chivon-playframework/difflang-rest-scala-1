package utils

import play.api.libs.json._
import reactivemongo.bson.{ BSONDocument, BSONObjectID }

import scala.util.Try
/**
 * Created by Kuylim on 10/26/2016.
 */
case class BSONObjectIDConverter(id: String) {
  val OId: Try[BSONObjectID] = BSONObjectID.parse(id)
  val selector = BSONDocument("_id" -> OId.get)
}
