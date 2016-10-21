package api

import play.api.libs.json._
import reactivemongo.api.QueryOpts

/**
 * Created by KUYLIM on 10/13/2016.
 */
class Pagination(page: Int, size: Int, totalCount: Int) extends QueryOpts {

  var Page = page
  var Size = size
  var TotalCount = totalCount
  var skip = (page - 1) * size

  var totalPage: Int = Math.ceil(TotalCount / size.asInstanceOf[Double]).toInt
}

object Pagination {
  implicit object PaginationWrites extends OWrites[Pagination] {
    def writes(pagination: Pagination): JsObject = Json.obj(
      "PAGE" -> pagination.Page,
      "SIZE" -> pagination.Size,
      "TOTAL_COUNT" -> pagination.TotalCount,
      "TOTAL_PAGE" -> pagination.totalPage
    )
  }
}
