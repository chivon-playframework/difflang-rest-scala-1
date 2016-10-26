package repos

import api.{ FilterData, Pagination }
import com.google.inject.ImplementedBy
import models.Translator
import play.api.libs.json.JsObject
import reactivemongo.api.commands.WriteResult
import repos.impl.TranslatorRepoImpl

import scala.concurrent.{ ExecutionContext, Future }

/**
 * Created by KUYLIM on 10/12/2016.
 */
@ImplementedBy(classOf[TranslatorRepoImpl])
trait TranslatorRepo {
  def find(pagination: Pagination, filterData: FilterData)(implicit ec: ExecutionContext): Future[List[JsObject]]

  def select(id: String)(implicit ec: ExecutionContext): Future[Option[JsObject]]

  def update(id: String, update: Translator)(implicit ec: ExecutionContext): Future[WriteResult]

  def remove(id: String)(implicit ec: ExecutionContext): Future[WriteResult]

  def save(document: Translator)(implicit ec: ExecutionContext): Future[WriteResult]

  def count()(implicit ec: ExecutionContext): Future[Int]

}
