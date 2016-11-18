package api

/**
 * Created by KUYLIM on 10/14/2016.
 */

case class FilterData(sort: String) {
  /*  var key: String = sort.toLowerCase()
  var value: Int = 1

  if (sort.contains('-')) {
    key = sort.trim.toLowerCase().split('-')(1)
    value = -1
  }*/

  var key: String = sort.toLowerCase()
  var value: Int = 1
}

