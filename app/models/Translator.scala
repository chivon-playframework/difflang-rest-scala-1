package models

import com.difflang.models.User1
import play.api.libs.json._
import play.modules.reactivemongo.json._

import scala.collection.mutable.ListBuffer
import reactivemongo.bson.BSONObjectID
import utils.BSONObjectIDConverter

import scala.util.Try

/**
 * Created by KUYLIM on 10/11/2016.
 */

case class Translator(
  _id: Option[BSONObjectID],
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
  translateExpertise: ListBuffer[TranslateExpertise], // TODO INJECT LIST OF TRANSLATE EXPERTISE
  user_id: Option[BSONObjectID]
)

//TODO CREATE BSONDocument FORMATE
object Translator {
  implicit val translatorFormat = Json.format[Translator]

}

