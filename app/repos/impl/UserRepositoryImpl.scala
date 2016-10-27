package repos.impl

import javax.inject.Inject

import api.{ FilterData, Pagination }
import com.difflang.models.User1
import play.api.libs.json.{ JsObject, Json }
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{ QueryOpts, ReadPreference }
import reactivemongo.play.json.collection.JSONCollection
import repos.UserRepository
import utils.BSONObjectIDConverter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ ExecutionContext, Future }

/**
 * Created by acer on 10/11/2016.
 */
class UserRepositoryImpl @Inject() (reactiveMongoApi: ReactiveMongoApi) extends UserRepository {

  def collection(implicit ec: ExecutionContext) = reactiveMongoApi.database.map(db => db.collection[JSONCollection]("USERS"))

  override def update(id: String, update: User1)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.update(BSONObjectIDConverter(id).selector, update))
  }

  override def remove(id: String)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.remove(BSONObjectIDConverter(id).selector))
  }

  override def select(id: String)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {
    collection.flatMap(_.find(BSONObjectIDConverter(id).selector).one[JsObject])
  }

  override def save(document: User1)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.insert(document))
  }

  override def findAll(pagination: Pagination, filterData: FilterData)(implicit ec: ExecutionContext): Future[List[JsObject]] = {
    val genericQueryBuilder = collection.map(_.find(Json.obj()).options(QueryOpts(pagination.skip)))
    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.Primary))
    cursor.flatMap(_.collect[List](pagination.Size))
  }

  def count()(implicit ec: ExecutionContext): Future[Int] = {
    collection.flatMap(_.count())
  }


  override def findByEmail(email: String)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {
    collection.flatMap(_.find(Json.obj("email" -> email)).one[JsObject])

  }

}
