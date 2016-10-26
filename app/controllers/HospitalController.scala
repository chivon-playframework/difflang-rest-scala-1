package controllers

import javax.inject.Inject

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc._
import reactivemongo.bson.{ BSONDocument, BSONObjectID }

import scala.concurrent.Await
import scala.concurrent.duration._
import models.Hospital
import api.{ FilterData, Pagination, WrappJson }
import play.api.i18n.MessagesApi
import repos.HospitalRepo

import scala.util.Try
/**
 * Created by CHHAI CHIVON on 10/21/2016.
 */

class HospitalController @Inject() (val hospitalService: HospitalRepo)(val messagesApi: MessagesApi) extends api.ApiController {

  //TODO GET ALL HOSPITAL
  def findAll(sort: String, page: Int, limit: Int) = SecuredApiAction { implicit request =>
    val sortData = new FilterData(sort)
    val getCount = Await.result(hospitalService.getTotalHospital(), 10 seconds)
    val pagination = new Pagination(page, limit, getCount)
    hospitalService.list(pagination, sortData).flatMap(hospital => ok(Json.toJson(WrappJson(hospital, pagination))))
  }

  //TODO ADD HOSPITAL
  def create = SecuredApiActionWithBody {
    implicit request =>
      val hospital = (request.body).as[Hospital]
      hospitalService.addHospital(hospital).flatMap(result => created())
  }

  //TODO GET HOSPITAL BY ID
  def findById(id: String) = SecuredApiAction {
    implicit request =>
      hospitalService.findHospitalById(id).flatMap(hospital => ok(Json.toJson(hospital)))
  }

  //TODO GET HOSPITAL BY EMIAL
  def findByEmail(email: String) = SecuredApiAction {
    implicit request =>
      hospitalService.findHospitalByEmail(email).flatMap(hospital => ok(Json.toJson(hospital)))
  }

  //TODO GET HOSPITAL BY NAME
  def findByName(name: String) = SecuredApiAction {
    implicit request =>
      hospitalService.findHospitalByName(name).flatMap(result => ok(Json.toJson(result)))
  }

  //TODO UPDATE HOSPITAL
  def update(id: String) = SecuredApiActionWithBody {
    {
      implicit request =>
        val hospital = (request.body).as[Hospital]
        hospitalService.updateHospital(id, hospital).flatMap(result => accepted())
    }
  }

  //TODO DELETE HOSPITAL
  def delete(id: String) = SecuredApiAction {
    implicit request =>
      hospitalService.deleteHospital(id).flatMap(_ => noContent())
  }

  //TODO GET TOTAL HOSPITAL
  def getTotalHospital = SecuredApiAction {
    implicit request =>
      hospitalService.getTotalHospital().flatMap(result => ok("Total :" + result.toString))
  }

}
