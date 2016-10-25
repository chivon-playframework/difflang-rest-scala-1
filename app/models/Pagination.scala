package models

import play.api.libs.json._
import reactivemongo.api.QueryOpts

/**
 * Created by Thanak on 10/13/2016.
 */
class Pagination(PAGES: Int, LIMITS: Int, TOTAL_COUNT: Int) extends QueryOpts {

  val PAGE = PAGES
  val TOTAL_COUNTS = TOTAL_COUNT
  val LIMIT = LIMITS
  val OFFSET = (PAGE - 1) * LIMITS
  val TOTAL_PAGE = Math.ceil(TOTAL_COUNT / LIMITS)

}

object Pagination {

  implicit object UserWrite extends Writes[Pagination] {
    def writes(pagination: Pagination): JsObject = Json.obj(

      "PAGES" -> pagination.PAGE,
      "TOTAL_COUNT" -> pagination.TOTAL_COUNTS,
      "LIMITS" -> pagination.LIMIT,
      "TOTAL_PAGES" -> pagination.TOTAL_PAGE,
      "OFFSET" -> pagination.OFFSET

    )
  }

}