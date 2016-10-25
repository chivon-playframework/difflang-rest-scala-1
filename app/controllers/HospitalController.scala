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
import repos.HospitalRepo
import scala.util.Try
/**
 * Created by CHHAI CHIVON on 10/21/2016.
 */

class HospitalController @Inject() (val hospitalService: HospitalRepo) extends Controller {

  //TODO GET ALL HOSPITAL
  def findAll(sort: String, page: Int, limit: Int) = Action.async {
    implicit request =>
      val sortData = new FilterData(sort)
      val getCount = Await.result(hospitalService.getTotalHospital(), 10 seconds)
      val pagination = new Pagination(page, limit, getCount)
      hospitalService.list(pagination, sortData).map(hospital => Ok(Json.toJson(WrappJson(hospital, pagination))))
  }

  //TODO ADD HOSPITAL
  def create = Action.async(BodyParsers.parse.json) {
    implicit request =>
      val hospital = (request.body).as[Hospital]
      hospitalService.addHospital(hospital).map(result => Created)
  }

  //TODO GET HOSPITAL BY ID
  def findById(id: String) = Action.async {
    val OId: Try[BSONObjectID] = BSONObjectID.parse(id)
    hospitalService.findHospitalById(BSONDocument("_id" -> OId.get)).map(hospital => Ok(Json.toJson(hospital)))
  }

  //TODO GET HOSPITAL BY NAME
  def findByName(name: String) = Action.async {
    hospitalService.findHospitalByName(name).map(result => Ok(Json.toJson(result)))
  }

  //TODO UPDATE HOSPITAL
  def update(id: String) = Action.async(BodyParsers.parse.json) {
    { implicit request =>
      val hospital = (request.body).as[Hospital]
      val OId: Try[BSONObjectID] = BSONObjectID.parse(id)
      val selector = BSONDocument("_id" -> OId.get)
      hospitalService.updateHospital(selector, hospital).map(result => Accepted)
    }
  }

  //TODO DELETE HOSPITAL
  def delete(id: String) = Action.async {
    val OId: Try[BSONObjectID] = BSONObjectID.parse(id)
    hospitalService.deleteHospital(BSONDocument("_id" -> OId.get))
      .map(result => Accepted)
  }

  //TODO GET TOTAL HOSPITAL
  def getTotalHospital = Action.async {
    hospitalService.getTotalHospital().map(result => Ok("Total :" + result.toString))
  }

}
