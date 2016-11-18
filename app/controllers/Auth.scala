package controllers

import api.ApiError._
import models.{ ApiToken }
import akka.actor.ActorSystem
import javax.inject.Inject
import com.difflang.models.User1
import play.api.i18n.MessagesApi
import play.api.libs.functional.syntax._
import play.api.libs.json._
import repos.UserRepository
import scala.concurrent.ExecutionContext.Implicits.global

class Auth @Inject() (val messagesApi: MessagesApi, system: ActorSystem, userRepo: UserRepository) extends api.ApiController {

  implicit val loginInfoReads: Reads[Tuple2[String, String]] = (
    (__ \ "email").read[String](Reads.email) and
      (__ \ "password").read[String] tupled
  )

  /*def signIn() = ApiActionWithBody { implicit request =>
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
  }  */

  def signIn() = ApiActionWithBody { implicit request =>
    readFromRequest[Tuple2[String, String]] {
      case (email, pwd) =>
        userRepo.findByEmail(email).flatMap {
          case None => errorUserNotFound
          case Some(user) => {
            if (user.password != pwd) errorUserNotFound
            else if (!user.confirm_email) errorUserEmailUnconfirmed
            else if (!user.active) errorUserInactive
            // userId should be chang to long if using with relational database
            else ApiToken.create(request.apiKeyOpt.get, user._id.toString).flatMap { token =>
              ok(Json.obj(
                "role" -> user.role,
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

  implicit val signUpInfoReads: Reads[Tuple3[String, String, User1]] = (
    (__ \ "email").read[String](Reads.email) and
      (__ \ "password").read[String](Reads.minLength[String](6)) and
      (__ \ "user").read[User1] tupled
  )

  /* def signUp = ApiActionWithBody { implicit request =>
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
  }*/

  def signUp = ApiActionWithBody { implicit request =>
    readFromRequest[Tuple3[String, String, User1]] {
      case (email, password, user1) =>
        userRepo.findByEmail(email).flatMap {
          case Some(anotherUser) => errorCustom("api.error.signup.email.exists")
          case None => {
            val user: User1 = User1(user1._id, user1.username, password, email, true, user1.created_date, true, user1.role)
            userRepo.save(user).flatMap(result => created("Insert Success"))
          }
        }
    }
  }

}