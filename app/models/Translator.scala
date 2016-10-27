package models

import play.api.libs.json._
import play.modules.reactivemongo.json._
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
  implicit val translatorFormat = Json.format[Translator]
}