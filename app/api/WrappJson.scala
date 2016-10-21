package api

import play.api.libs.json._

case class WrappJson(
  data: List[JsObject],
  pagination: Pagination
)

object WrappJson {
  implicit object jsonWrites extends OWrites[WrappJson] {
    def writes(wrapJson: WrappJson): JsObject = Json.obj(
      "DATA" -> wrapJson.data,
      "PAGINATION" -> wrapJson.pagination,
      "MESSAGE" -> "OK"
    )
  }
}

