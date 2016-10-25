package models

import play.api.libs.json._

/**
 * Created by KUYLIM on 10/13/2016.
 */
case class WrappJson(
  data: List[JsObject],
  pagination: Pagination
)

object WrappJson {
  implicit object jsonWrites extends OWrites[WrappJson] {
    def writes(wrapJson: WrappJson): JsObject = Json.obj(
      "DATA" -> wrapJson.data,
      "PAGINATION" -> wrapJson.pagination,
      "PAGINATION" -> wrapJson.pagination,
      "MESSAGE" -> "OK"
    )
  }
}