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

  makePagination.page = Page
  makePagination.size = Size
  makePagination.totalCount = TotalCount
  makePagination.totalPage = totalPage
}

object makePagination {
  var page = 0
  var size = 0
  var totalCount = 0
  var totalPage = 0

  def makePagination(): JsObject = Json.obj(
    "page" -> page,
    "size" -> size,
    "total_count" -> totalCount,
    "total_pages" -> totalPage
  )
}

