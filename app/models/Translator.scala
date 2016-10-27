package models

import play.api.libs.json._

import scala.collection.mutable.ListBuffer

/**
 * Created by KUYLIM on 10/11/2016.
 */
case class Translator(
  firstname: String,
  lastname: String,
  companyName: String,
  email: String,
  phone: String,
  address: String,
  state: String,
  zipcode: String,
  targetLanguage: ListBuffer[Language], // TODO INJECT LIST OF LANGUAGE
  nativeLanguage: String,
  officialDegree: Boolean,
  translateSince: Int,
  serviceTranslate: ListBuffer[ServiceTranslate], // TODO INJECT LIST OF SERVICE TRANSLATE
  translateExpertise: ListBuffer[TranslateExpertise] // TODO INJECT LIST OF TRANSLATE EXPERTISE
)

//TODO CREATE BSONDocument FORMATE
object Translator {

  implicit object TranslatorWrite extends OWrites[Translator] {
    def writes(translator: Translator): JsObject = Json.obj(

      "firstname" -> translator.firstname,
      "lastname" -> translator.lastname,
      "company_name" -> translator.companyName,
      "email" -> translator.email,
      "phone" -> translator.phone,
      "address" -> translator.address,
      "state" -> translator.state,
      "zipcode" -> translator.zipcode,
      "target_language" -> (translator.targetLanguage),
      "native_language" -> translator.nativeLanguage,
      "official_degree" -> translator.officialDegree,
      "translate_since" -> translator.translateSince,
      "service_translate" -> Json.arr(translator.serviceTranslate),
      "translate_expertise" -> Json.arr(translator.translateExpertise)
    )
  }

  implicit object TranslatorReads extends Reads[Translator] {
    def reads(json: JsValue): JsResult[Translator] = json match {
      case obj: JsObject => try {

        val firstname = (obj \ "firstname").as[String]
        val lastname = (obj \ "lastname").as[String]
        val companyName = (obj \ "company_name").as[String]
        val email = (obj \ "email").as[String]
        val phone = (obj \ "phone").as[String]
        val address = (obj \ "address").as[String]
        val state = (obj \ "state").as[String]
        val zipcode = (obj \ "zipcode").as[String]
        val targetLanguage = (obj \ "target_language").as[ListBuffer[Language]]
        val nativeLanguage = (obj \ "native_language").as[String]
        val officialDegree = (obj \ "official_degree").as[Boolean]
        val translateSince = (obj \ "translate_since").as[Int]
        val serviceTranslate = (obj \ "service_translate").as[ListBuffer[ServiceTranslate]]
        val translateExpertise = (obj \ "translate_expertise").as[ListBuffer[TranslateExpertise]]
        JsSuccess(Translator(firstname, lastname, companyName, email, phone, address, state, zipcode, targetLanguage, nativeLanguage, officialDegree, translateSince, serviceTranslate, translateExpertise))
      } catch {
        case cause: Throwable => JsError(cause.getMessage)
      }
      case _ => JsError("expected.jsobject")
    }
  }

}