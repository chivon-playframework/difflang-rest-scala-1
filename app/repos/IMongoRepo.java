package repos;

import org.bson.Document;
import scala.concurrent.Promise;
import java.util.List;
import java.util.concurrent.*;

interface IMongoRepo {
    void setCollection(String collectionName);
    CompletionStage<List<Document>> find();
    CompletionStage<List<Document>> findEqual(String field, String value);
}
