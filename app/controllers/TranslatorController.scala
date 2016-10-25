package controllers

import javax.inject.Inject

import api.{ FilterData, Pagination, WrappJson }
import models.Translator
import play.api.i18n.MessagesApi
import play.api.libs.json.Json
import play.api.mvc._
import reactivemongo.bson.{ BSONDocument, BSONObjectID }
import repos.TranslatorRepo

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Try

/**
 * Created by KUYLIM on 10/11/2016.
 */
class TranslatorController @Inject() (val translatorService: TranslatorRepo, val messagesApi: MessagesApi) extends api.ApiController {

  // def translatorService = new TranslatorRepoImpl(reactiveMongoApi)

  // TODO ADD ALL TRANSLATOR
  /*def create = Action.async(BodyParsers.parse.json) {
    implicit request =>
      val translator = (request.body).as[Translator]
      translatorService.save(translator).map(result => Created)
  }*/

  def create = SecuredApiActionWithBody {
    implicit request =>
      val translator = (request.body).as[Translator]
      //val translator = readFromRequest[Translator]
      translatorService.save(translator).flatMap(result => created())
  }

  /* def insert = SecuredApiActionWithBody { implicit request =>
    readFromRequest[Folder] { folder =>
      Folder.insert(request.userId, folder.name).flatMap {
        case (id, newFolder) => created(Api.locationHeader(routes.Folders.info(id)))
      }
    }
  } */

  // TODO GET ALL TRANSLATOR
  def findAll(sort: String, page: Int, size: Int) = SecuredApiAction {
    implicit request =>
      val sortData = new FilterData(sort)
      val totalCount = Await.result(translatorService.count(), 10 seconds)
      val pagination = new Pagination(page, size, totalCount)
      translatorService.find(pagination, sortData).flatMap(translator => ok(Json.toJson(WrappJson(translator, pagination))))
  }

  /*// TODO FIND TRANSLATOR BY ID
  def findById(id: String) = Action.async {
    val OId: Try[BSONObjectID] = BSONObjectID.parse(id)
    translatorService.select(BSONDocument("_id" -> OId.get)).map(translator => Ok(Json.toJson(translator)))
  }*/
  // TODO FIND TRANSLATOR BY ID
  def findById(id: String) = SecuredApiAction {
    implicit request =>
      val OId: Try[BSONObjectID] = BSONObjectID.parse(id)
      translatorService.select(BSONDocument("_id" -> OId.get)).flatMap(translator => ok(Json.toJson(translator)))
  }

  // TODO UPDATE TRANSLATOR
  def update(id: String) = Action.async(BodyParsers.parse.json) {
    {
      implicit request =>
        val translator = (request.body).as[Translator]
        val OId: Try[BSONObjectID] = BSONObjectID.parse(id)
        val selector = BSONDocument("_id" -> OId.get)
        translatorService.update(selector, translator).map(result => Accepted)
    }
  }

  // TODO DELETE TRANSLATOR
  def delete(id: String) = Action.async {
    val OId: Try[BSONObjectID] = BSONObjectID.parse(id)
    translatorService.remove(BSONDocument("_id" -> OId.get))
      .map(result => Accepted)
  }

}
