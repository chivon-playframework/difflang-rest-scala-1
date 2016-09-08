package repos;

import enums.Env;
import org.bson.Document;
import java.util.List;
import java.util.concurrent.*;

interface IMongoRepo {
    void init(Env env);
    void setCollection(String collectionName);
    void createCollection(String collectionName);
    CompletableFuture<List<Document>> find();
    CompletableFuture<List<Document>> findEqual(String field, String value);
}
