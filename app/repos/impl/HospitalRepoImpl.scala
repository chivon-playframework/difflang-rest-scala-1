package repos.impl

import repos.HospitalRepo
import javax.inject.Inject

import api.{ FilterData, Pagination }
import reactivemongo.api.{ QueryOpts, ReadPreference }
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ ExecutionContext, Future }
import play.modules.reactivemongo.json._
import play.api.libs.json.{ JsObject, Json }
import play.modules.reactivemongo.ReactiveMongoApi
import models.Hospital
import utils.BSONObjectIDConverter

/**
 * Created by CHHAI CHIVON
 */

class HospitalRepoImpl @Inject() (reactiveMongoApi: ReactiveMongoApi) extends HospitalRepo {

  //TODO GET CONNECTION DB
  def collection(implicit ec: ExecutionContext) = reactiveMongoApi.database.map(db => db.collection[JSONCollection]("HOSPITALS"))

  //TODO GET ALL HOSPITAL
  override def list(pagination: Pagination, sort: FilterData)(implicit ec: ExecutionContext): Future[List[JsObject]] = {

    val genericQueryBuilder = collection.map(_.find(Json.obj()).options(QueryOpts(pagination.skip)).sort(Json.obj(sort.key -> sort.value)))
    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.Primary))
    cursor.flatMap(_.collect[List](pagination.Size))
  }

  //TODO GET HOSPITAL BY ID
  override def findHospitalById(id: String)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {
    collection.flatMap(_.find(BSONObjectIDConverter(id).selector).one[JsObject])
  }

  //TODO FIND HOSPITAL BY NAME
  override def findHospitalByName(name: String)(implicit ec: ExecutionContext): Future[List[JsObject]] = {
    val genericQueryBuilder = collection.map(_.find(Json.obj("NAME" -> name)))
    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.Primary))
    cursor.flatMap(_.collect[List]())
  }

  //TODO ADD HOSPITAL
  override def addHospital(hospital: Hospital)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.insert(hospital))
  }

  //TODO DELETE HOSPITAL
  override def deleteHospital(id: String)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.remove(BSONObjectIDConverter(id).selector))
  }

  //TODO UPDATE HOSPITAL
  override def updateHospital(id: String, update: Hospital)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.update(BSONObjectIDConverter(id).selector, update))
  }

  //TODO GET TOTAL HOSPITAL
  override def getTotalHospital()(implicit ec: ExecutionContext): Future[Int] = {
    collection.flatMap(_.count())
  }

}