package api

/**
 * Created by acer on 10/25/2016.
 */
case class FilterData(
    sort: String
) {
  var key: String = sort.toUpperCase()
  var value: Int = 1
  if (sort.contains('-')) {
    key = sort.trim.toUpperCase.split('-')(1)
    value = -1
  }
}
