package repos.impl

import javax.inject.Inject

import api.{ FilterData, Pagination }
import com.twitter.util.Config.intoOption
import models.{ Translator, User }
import play.api.libs.json.{ JsObject, Json }
import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{ QueryOpts, ReadPreference }
import reactivemongo.bson
import reactivemongo.bson.{ BSONDocument, BSONObjectID }
import reactivemongo.play.json.collection.JSONCollection
import repos.TranslatorRepo
import utils.BSONObjectIDConverter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ ExecutionContext, Future }
import scala.util.Try

/**
 * Created by Kuylim on 10/21/2016.
 */
class TranslatorRepoImpl @Inject() (reactiveMongoApi: ReactiveMongoApi) extends TranslatorRepo {

  def collection(implicit ec: ExecutionContext) = reactiveMongoApi.database.map(_.collection[JSONCollection]("TRANSLATORS"))

  override def find(pagination: Pagination, sort: FilterData)(implicit ec: ExecutionContext): Future[List[JsObject]] = {
    import reactivemongo.play.json.collection.JSONBatchCommands.AggregationFramework.{ Lookup, Ascending, Descending, Limit, Project, Skip, Sort }
    try {
      def order(str: String) = sort.value match {
        case 1 => Ascending(str)
        case -1 => Descending(str)
        case _ => Ascending(str)
      }
      collection.flatMap(_.aggregate(
        Lookup(
          "USERS",
          OId,
          "_id",
          "user"
        ),
        List(
          Project(
            Json.obj(
              "firstname" -> 1,
              "lastname" -> 1,
              "email" -> 1,
              "languages" -> 1,
              "user_id" -> Json.obj("$user_id" -> ("$" + OId).get),
              "user" -> 1,
              "output" -> Json.obj("$toLower" -> ("$" + sort.key))
            )
          ),
          Sort(order("output")),
          Skip(pagination.skip),
          Limit(pagination.Size)
        /*,
          Project(
            Json.obj(
              "firstname" -> 1,
              "lastname" -> 1,
              "email" -> 1,
              "languages" -> 1,
              "user_id" -> 1,
              "user" -> 1
            )
          )*/
        )
      ).map(_.head[JsObject]))
    } catch {
      case _: Throwable => Future(null)
    }

    /*val genericQueryBuilder = collection.map(_.find(Json.obj()).options(QueryOpts(pagination.skip)).sort(Json.obj(sort.key -> sort.value)))
    val cursor = genericQueryBuilder.map(_.cursor[JsObject](ReadPreference.Primary))
    cursor.flatMap(_.collect[List](pagination.Size))*/
  }

  override def update(id: String, update: Translator)(implicit ec: ExecutionContext): Future[WriteResult] =
    {
      collection.flatMap(_.update(BSONObjectIDConverter(id).selector, update))
    }

  override def remove(id: String)(implicit ec: ExecutionContext): Future[WriteResult] =
    {
      collection.flatMap(_.remove(BSONObjectIDConverter(id).selector))
    }

  override def select(id: String)(implicit ec: ExecutionContext): Future[Option[JsObject]] = {

    collection.flatMap(_.find(BSONObjectIDConverter(id).selector).one[JsObject])
  }

  override def save(document: Translator)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.flatMap(_.insert(document))
  }

  override def count()(implicit ec: ExecutionContext): Future[Int] =
    {
      collection.flatMap(_.count())
    }
}
