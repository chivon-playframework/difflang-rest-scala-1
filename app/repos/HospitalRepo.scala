package repos

import javax.inject.Inject

import com.google.inject.ImplementedBy
import play.api.libs.json.JsObject
import repos.impl.HospitalRepoImpl

import scala.concurrent.{ ExecutionContext, Future }
import api.Pagination
import api.FilterData
import models.Hospital
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument


/**
 * Created by CHHAI CHIVON
 */

@ImplementedBy(classOf[HospitalRepoImpl])
trait HospitalRepo {
  def list(pagination: Pagination, sort: FilterData)(implicit ec: ExecutionContext): Future[List[JsObject]]
  def findHospitalById(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]]
  def findHospitalByName(name: String)(implicit ec: ExecutionContext): Future[List[JsObject]]
  def addHospital(hospital: Hospital)(implicit ec: ExecutionContext): Future[WriteResult]
  def deleteHospital(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]
  def updateHospital(id: BSONDocument, update: Hospital)(implicit ec: ExecutionContext): Future[WriteResult]
  def getTotalHospital()(implicit ec: ExecutionContext): Future[Int]

}