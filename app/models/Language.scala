package models

import play.api.libs.json._
import play.modules.reactivemongo.json._
/**
 * Created by KUYLIM on 10/11/2016.
 */
case class Language(
  language: String
)

object Language {
  implicit val languageFormat = Json.format[Language]
}
