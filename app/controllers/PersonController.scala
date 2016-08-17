package controllers

import javax.inject._
import play.api.Logger
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import play.api.libs.json._
import play.api.mvc._
import play.modules.reactivemongo._
import reactivemongo.api.ReadPreference
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.{ExecutionContext, Future} // Future and its EC

// A bit more complex controller using a Json coast-to-coast approach.
// There is no model for Person and some data created dynamically
// Input is directly converted to JsObject to be stored in MongoDB
@Singleton        // Injection scope
class PersonController @Inject()(val reactiveMongoApi: ReactiveMongoApi)
                                (implicit exec: ExecutionContext) extends Controller
                                with MongoController with ReactiveMongoComponents {

  // Create JsObject with name, age, and created fields
  val transformer: Reads[JsObject] =
    Reads.jsPickBranch[JsString](__ \ "name") and
      Reads.jsPickBranch[JsNumber](__ \ "age") and
      Reads.jsPut(__ \ "created", JsNumber(new java.util.Date().getTime())) reduce

  // Gets persons collection from the database
  def personsFuture: Future[JSONCollection] = database.map(_.collection[JSONCollection]("persons"))

  // Given name and age, we will construct the Json object and insert it into
  // persons collection of MongoDB
  def create(name: String, age: Int) = Action.async {
    // Create Json object key-value Map
    val json = Json.obj(
      "name" -> name,
      "age" -> age,
      "created" -> new java.util.Date().getTime()
    )
    for {
      persons <- personsFuture // Get persons collection from future
      lastError <- persons.insert(json) // Insert Json object and get any error
    } yield Ok("MongoDB LastError: %s".format(lastError))
  }

  // Given Json object from request
  // We need to we need to transform the Json request body into
  // Person instance and insert() it into the persons collection of MongoDB.
  def createFromJson = Action.async(parse.json) { request =>
    request.body.transform(transformer) match {
      case JsSuccess(person, _) =>
        for {
          persons <- personsFuture
          lastError <- persons.insert(person)
        } yield {
          Logger.debug(s"Successfully inserted with LastError: $lastError")
          Created("Created 1 person")
        }
      case _ =>
        Future.successful(BadRequest("Invalid Json"))
    }
  }

  // Given the user requests to create a list of persons
  // We need to be able to insert all of them into the collections
  def createBulkFromJson = Action.async(parse.json) { request =>
    val docs = for {
      persons <- request.body.asOpt[JsArray].toStream
      maybePerson <- persons.value
      validPerson <- maybePerson.transform(transformer).asOpt.toList
    } yield validPerson
    for {
      persons <- personsFuture
      multiResult <- persons.bulkInsert(documents = docs, ordered = true)
    } yield {
      Logger.debug(s"Successfully inserted with multiResult: $multiResult")
      Created(s"Created ${multiResult.n} person")
    }
  }

  def findByName(name: String) = Action.async {
    val cursor: Future[List[JsObject]] = personsFuture.flatMap{ persons =>
      persons.find(Json.obj("name" -> name))
        .sort(Json.obj("created" -> -1))
        .cursor[JsObject](ReadPreference.primary).collect[List]()
    }
    cursor.map { persons =>
      Ok(Json.toJson(persons))
    }
  }
}
