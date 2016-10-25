package controllers

import javax.inject.Inject

import api.FilterData
import com.difflang.models.User1
import models.{ Pagination, WrappJson }
import play.api.libs.json.Json
import play.api.mvc._
import play.modules.reactivemongo.{ MongoController, ReactiveMongoApi, ReactiveMongoComponents }
import reactivemongo.bson.{ BSONDocument, BSONObjectID }
import repos.UserRepository

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Try
import scala.concurrent.Await
import scala.concurrent.duration._

/**
 * Created by acer on 10/11/2016.
 */
class UserController @Inject() (val reactiveMongoApi: ReactiveMongoApi, userRepo: UserRepository) extends Controller with MongoController with ReactiveMongoComponents {

  //def userRepo = new UserRepositoryImpl(reactiveMongoApi)

  def index = Action.async {
    implicit request =>

      userRepo.find().map(users => Ok(Json.toJson(users)))
  }

  def create = Action.async(BodyParsers.parse.json) { implicit request =>
    val user = (request.body).as[User1]

    userRepo.save(user).map(result => Created)
  }

  def read(id: String) = Action.async {
    val OId: Try[BSONObjectID] = BSONObjectID.parse(id)
    userRepo.select(BSONDocument("_id" -> OId.get)).map(user => Ok(Json.toJson(user)))
  }

  def update(id: String) = Action.async(BodyParsers.parse.json) {
    {
      implicit request =>
        val user = (request.body).as[User1]
        val OId: Try[BSONObjectID] = BSONObjectID.parse(id)
        val selector = BSONDocument("_id" -> OId.get)

        val modifier = (user)
        userRepo.update(selector, modifier).map(result => Accepted)
    }
  }

  def delete(id: String) = Action.async {
    val OId: Try[BSONObjectID] = BSONObjectID.parse(id)
    userRepo.remove(BSONDocument("_id" -> OId.get)).map(result => Accepted)
  }

  def findPaginate(sort: String, page: Int, limit: Int) = Action.async {
    val sortData = new FilterData(sort)
    val number1 = Await.result(userRepo.count1(), 10 seconds)
    val pagination = new Pagination(page, limit, number1)
    userRepo.findByPaginate(pagination, sortData).map(users => Ok(Json.toJson(WrappJson(users, pagination))))
  }

}
