package repos

import api.{ FilterData, Pagination }
import com.google.inject.ImplementedBy
import models.Translator
import play.api.libs.json.JsObject
import reactivemongo.api.commands.WriteResult
import reactivemongo.bson.BSONDocument
import repos.impl.TranslatorRepoImpl

import scala.concurrent.{ ExecutionContext, Future }

/**
 * Created by nationalist on 9/26/16.
 */
@ImplementedBy(classOf[TranslatorRepoImpl])
trait TranslatorRepo {
  def find(pagination: Pagination, filterData: FilterData)(implicit ec: ExecutionContext): Future[List[JsObject]]

  def select(id: BSONDocument)(implicit ec: ExecutionContext): Future[Option[JsObject]]

  def update(id: BSONDocument, update: Translator)(implicit ec: ExecutionContext): Future[WriteResult]

  def remove(id: BSONDocument)(implicit ec: ExecutionContext): Future[WriteResult]

  def save(document: Translator)(implicit ec: ExecutionContext): Future[WriteResult]

  def count()(implicit ec: ExecutionContext): Future[Int]
}
