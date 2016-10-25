package repos

import api.FilterData
import com.difflang.models.User1
import com.google.inject.ImplementedBy
import impl.UserRepositoryImpl
import models.Pagination
import play.api.libs.json.JsObject
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument

import scala.concurrent.{ ExecutionContext, Future }

/**
 * Created by Thanak on 10/11/2016.
 */
@ImplementedBy(classOf[UserRepositoryImpl])
trait UserRepository {
  def find()(implicit ec: ExecutionContext): Future[List[JsObject]]

  def select(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]]

  def update(id: BSONDocument, update: User1)(implicit ec: ExecutionContext): Future[WriteResult]

  def remove(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]

  def save(document: User1)(implicit ec: ExecutionContext): Future[WriteResult]

  def findByPaginate(pagination: Pagination, fiterData: FilterData)(implicit ec: ExecutionContext): Future[List[JsObject]]

  def count1()(implicit ec: ExecutionContext): Future[Int]
}
