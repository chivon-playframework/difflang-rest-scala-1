package repos

import api.{ FilterData, Pagination }
import com.difflang.models.User1
import com.google.inject.ImplementedBy
import impl.UserRepositoryImpl
import play.api.libs.json.JsObject
import reactivemongo.api.commands.WriteResult
import scala.concurrent.{ ExecutionContext, Future }

/**
 * Created by Thanak on 10/11/2016.
 */
@ImplementedBy(classOf[UserRepositoryImpl])
trait UserRepository {

  def select(id: String)(implicit ec: ExecutionContext): Future[Option[JsObject]]

  def update(id: String, update: User1)(implicit ec: ExecutionContext): Future[WriteResult]

  def remove(id: String)(implicit ec: ExecutionContext): Future[WriteResult]

  def save(document: User1)(implicit ec: ExecutionContext): Future[WriteResult]

  def findAll(pagination: Pagination, fiterData: FilterData)(implicit ec: ExecutionContext): Future[List[JsObject]]

  def count()(implicit ec: ExecutionContext): Future[Int]

  def findByEmail2(email: String)(implicit ec: ExecutionContext): Future[Option[User1]]
}
