package models
import play.modules.reactivemongo.json._
import play.api.libs.json._

/**
 * Created by KUYLIM on 10/11/2016.
 */
case class ServiceTranslate(
  serviceTranslate: String
)

object ServiceTranslate {
  implicit val serviceFormat = Json.format[ServiceTranslate]
}
