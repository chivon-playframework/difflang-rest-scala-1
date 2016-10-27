package controllers

import javax.inject.Inject

import akka.actor.ActorSystem
import api.ApiError._
import models.{ ApiToken, User }
import play.api.i18n.MessagesApi
import play.api.libs.json._

/**
 * Created by CHHAI CHIVON
 */
/*
class Authentication @Inject() (val messageApi: MessagesApi, system: ActorSystem) extends api.ApiController {

  implicit val loginInfoReads: Reads[Tuple2[String, String]] = (
    (__ \ "email").read[String](Reads.email) and
      (__ \ "password").read[String] tupled
  )

  def userSignIn = ApiActionWithBody { implicit request =>
    readFromRequest[Tuple2[String, String]] {
      case (email, password) =>
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

}
*/
