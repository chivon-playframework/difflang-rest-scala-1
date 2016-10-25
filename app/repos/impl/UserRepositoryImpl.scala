package repos.impl

import javax.inject.Inject

import api.FilterData
import com.difflang.models.User1
import models.Pagination
import play.api.libs.json.{ JsObject, Json }
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{ QueryOpts, ReadPreference }
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json.collection.JSONCollection
import repos.UserRepository

import scala.concurrent.{ ExecutionContext, Future }

/**
 * Created by acer on 10/11/2016.
 */
class UserRepositoryImpl @Inject() (reactiveMongoApi: ReactiveMongoApi) extends UserRepository {

  def collection(implicit ec: ExecutionContext) = reactiveMongoApi.database.map(db => db.collection[JSONCollection]("USERS"))

  override def find()(implicit ec: ExecutionContext): Future[List[JsObject]] = {
    val genericQueryBuilder = collection.map(_.find(Json.obj()))
    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.primary))
    cursor.flatMap(_.collect[List]())

  }

  override def update(id: BSONDocument, update: User1)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.update(id, update))
  }

  override def remove(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.remove(id))
  }

  override def select(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {
    collection.flatMap(_.find(id).one[JsObject])
  }

  override def save(document: User1)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.insert(document))
  }

  override def findByPaginate(pagination: Pagination, filterData: FilterData)(implicit ec: ExecutionContext): Future[List[JsObject]] = {
    val genericQueryBuilder = collection.map(_.find(Json.obj()).options(QueryOpts(pagination.OFFSET)))
    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.Primary))
    cursor.flatMap(_.collect[List](pagination.LIMIT))
  }

  def count1()(implicit ec: ExecutionContext): Future[Int] = {
    val num = collection.flatMap(_.count())
    num
  }

}
