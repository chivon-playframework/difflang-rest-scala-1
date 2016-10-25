package repos.impl

import javax.inject.Inject

import api.{ FilterData, Pagination }
import models.Translator

import play.api.libs.json.{ JsObject, Json }
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{ QueryOpts, ReadPreference }
import reactivemongo.bson.BSONDocument
import reactivemongo.play.json.collection.JSONCollection
import repos.TranslatorRepo
import play.modules.reactivemongo.json._
import scala.concurrent.{ ExecutionContext, Future }

/**
 * Created by Kuylim on 10/21/2016.
 */
class TranslatorRepoImpl @Inject() (reactiveMongoApi: ReactiveMongoApi) extends TranslatorRepo {

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
