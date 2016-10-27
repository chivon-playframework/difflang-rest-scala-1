package models
import play.modules.reactivemongo.json._
import play.api.libs.json._

/**
 * Created by KUYLIM on 10/11/2016.
 */
case class TranslateExpertise(
  tranExpert: String
)

object TranslateExpertise {
  implicit val translateExpertiseFormat = Json.format[TranslateExpertise]
}
