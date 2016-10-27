package controllers

import javax.inject.Inject

import api.{ FilterData, Pagination }
import models.Translator
import play.api.i18n.MessagesApi
import repos.TranslatorRepo

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

/**
 * Created by KUYLIM on 10/11/2016.
 */
class TranslatorController @Inject() (val translatorService: TranslatorRepo, val messagesApi: MessagesApi) extends api.ApiController {

  def create = SecuredApiActionWithBody {
    implicit request =>
      val translator = (request.body).as[Translator]
      translatorService.save(translator).flatMap(result => created("Translator save successfully!"))
  }

  // TODO GET ALL TRANSLATOR
  def findAll(sort: String, page: Int, size: Int) = SecuredApiAction {
    implicit request =>
      val sortData = new FilterData(sort)
      val totalCount = Await.result(translatorService.count(), 10 seconds)
      val pagination = new Pagination(page, size, totalCount)
      translatorService.find(pagination, sortData).flatMap(translator => ok(translator))
  }

  // TODO FIND TRANSLATOR BY ID
  def findById(id: String) = SecuredApiAction {
    implicit request =>
      translatorService.select(id).flatMap(translator => ok(translator))
  }

  // TODO UPDATE TRANSLATOR
  def update(id: String) = SecuredApiActionWithBody {
    {
      implicit request =>
        val translator = (request.body).as[Translator]
        translatorService.update(id, translator).flatMap(result => accepted("Translator update successfully!"))
    }
  }

  // TODO DELETE TRANSLATOR
  def delete(id: String) = SecuredApiAction {
    implicit request =>
      translatorService.remove(id)
        .flatMap(_ => accepted("Translator delete successfully!"))
  }
}
