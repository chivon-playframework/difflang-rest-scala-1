package repos

import javax.inject.Inject

import models.Translator
import api.{ FilterData, Pagination }
import com.google.inject.ImplementedBy
import play.api.libs.json.{ JsObject, Json }
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.api.{ QueryOpts, ReadPreference }
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ ExecutionContext, Future }

/**
 * Created by KUYLIM on 10/12/2016.
 */
@ImplementedBy(classOf[TranslatorRepoImpl])
trait TranslatorRepository {
  def find(pagination: Pagination, filterData: FilterData)(implicit ec: ExecutionContext): Future[List[JsObject]]

  def select(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]]

  def update(id: BSONDocument, update: Translator)(implicit ec: ExecutionContext): Future[WriteResult]

  def remove(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]

  def save(document: Translator)(implicit ec: ExecutionContext): Future[WriteResult]

  def count()(implicit ec: ExecutionContext): Future[Int]

}

/*Implement class*/

class TranslatorRepoImpl @Inject() (reactiveMongoApi: ReactiveMongoApi) extends TranslatorRepository {

  def collection(implicit ec: ExecutionContext) = reactiveMongoApi.database.map(_.collection[JSONCollection]("TRANSLATORS"))

  override def find(pagination: Pagination, sort: FilterData)(implicit ec: ExecutionContext): Future[List[JsObject]] = {
    val genericQueryBuilder = collection.map(_.find(Json.obj()).options(QueryOpts(pagination.skip)).sort(Json.obj(sort.key -> sort.value)))
    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.Primary))
    cursor.flatMap(_.collect[List](pagination.Size))
  }

  override def update(id: BSONDocument, update: Translator)(implicit ec: ExecutionContext): Future[WriteResult] =
    {
      collection.flatMap(_.update(id, update))
    }

  override def remove(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult] =
    {
      collection.flatMap(_.remove(id))
    }

  override def select(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]] =
    {
      collection.flatMap(_.find(id).one[JsObject])
    }

  override def save(document: Translator)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.insert(document))
  }

  override def count()(implicit ec: ExecutionContext): Future[Int] =
    {
      collection.flatMap(_.count())
    }
}