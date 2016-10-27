package controllers

import javax.inject.Inject

import api.{ FilterData, Pagination }
import models.User1
import play.api.i18n.MessagesApi
import play.api.libs.json._
import repos.UserRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * Created by Thanak on 10/11/2016.
 */
class UserController @Inject() (val userService: UserRepository, val messagesApi: MessagesApi) extends api.ApiController {

  // TODO INSERT USER
  def create = SecuredApiActionWithBody { implicit request =>
    val user = (request.body).as[User1]
    userService.save(user).flatMap(result => created("Insert Success"))
  }
  // TODO SELECT BY ID
  def findById(id: String) = SecuredApiAction { implicit request =>
    userService.select(id).flatMap(user => ok(user))
  }

  // TODO UPDATE BY ID
  def update(id: String) = SecuredApiActionWithBody {
    implicit request =>
      val user = (request.body).as[User1]
      userService.update(id, user).flatMap(result => accepted("Update Success"))
  }

  // TODO DELETE BY ID
  def delete(id: String) = SecuredApiAction { implicit request =>
    userService.remove(id).flatMap(_ => accepted("Delete success"))
  }
  // TODO FIND ALL USERS
  def findAll(sort: String, page: Int, limit: Int) = SecuredApiAction { implicit request =>
    val sortData = new FilterData(sort)
    val number1 = Await.result(userService.count(), 10 seconds)
    val pagination = new Pagination(page, limit, number1)
    userService.findAll(pagination, sortData).flatMap(users => ok(users))
  }
  // TODO FIND USER BY EMAIL
  def findByEmail(email: String) = SecuredApiAction { implicit request =>
    userService.findByEmail(email).flatMap(user => ok(user))
  }

}
