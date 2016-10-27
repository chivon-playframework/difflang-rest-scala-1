package controllers

import api.ApiError._
import api.JsonCombinators._
import models.{ ApiToken, User }
import play.api.mvc._
import play.api.libs.json._

import play.api.libs.functional.syntax._
import play.api.Play.current
import akka.actor.ActorSystem

import scala.concurrent.duration._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import javax.inject.Inject

import com.difflang.models.User1
import play.api.i18n.MessagesApi
import repos.UserRepository

class Auth @Inject() (val messagesApi: MessagesApi, system: ActorSystem, userService: UserRepository) extends api.ApiController {

  implicit val loginInfoReads: Reads[Tuple2[String, String]] = (
    (__ \ "email").read[String](Reads.email) and
      (__ \ "password").read[String] tupled
  )

  def signIn = ApiActionWithBody { implicit request =>
    readFromRequest[Tuple2[String, String]] {
      case (email, pwd) =>
        User.findByEmail(email).flatMap {
          case None => errorUserNotFound
          case Some(user) => {
            if (user.password != pwd) errorUserNotFound
            else if (!user.emailConfirmed) errorUserEmailUnconfirmed
            else if (!user.active) errorUserInactive
            else ApiToken.create(request.apiKeyOpt.get, user.id).flatMap { token =>
              ok(Json.obj(
                "token" -> token,
                "minutes" -> 10
              ))
            }
          }
        }
    }
  }

  def signOut = SecuredApiAction { implicit request =>
    ApiToken.delete(request.token).flatMap { _ =>
      noContent()
    }
  }

  implicit val signUpInfoReads: Reads[Tuple3[String, String, User]] = (
    (__ \ "email").read[String](Reads.email) and
      (__ \ "password").read[String](Reads.minLength[String](6)) and
      (__ \ "user").read[User] tupled
  )

  def signUp = ApiActionWithBody { implicit request =>
    readFromRequest[Tuple3[String, String, User]] {
      case (email, password, user) =>
        User.findByEmail(email).flatMap {
          case Some(anotherUser) => errorCustom("api.error.signup.email.exists")
          case None => User.insert(email, password, user.name).flatMap {
            case (id, user) =>

              // Send confirmation email. You will have to catch the link and confirm the email and activate the user.
              // But meanwhile...
              system.scheduler.scheduleOnce(30 seconds) {
                User.confirmEmail(id)
              }

              ok(user)
          }
        }
    }
  }

  /*def signUp1 = ApiActionWithBody { implicit request =>

    val user = (request.body).as[User1]
  def matchEmail(user1: User1):Boolean= {
    val email:String=userService.findByEmail(user1.email)
    case (user.email==)
    userService.save(user).flatMap(result => created())

  }
  }*/
}