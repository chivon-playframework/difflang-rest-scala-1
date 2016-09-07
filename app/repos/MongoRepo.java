package repos;

import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import enums.Env;
import org.bson.Document;
import play.Configuration;
import scala.concurrent.Promise;
import java.util.concurrent.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * The MongoDB repository
 * https://www.playframework.com/documentation/2.5.0/api/java/play/Configuration.html
 * https://groups.google.com/forum/#!topic/play-framework/degqSGUdWK8
 * https://github.com/jonasanso/play-reactive-mongo-db
 * http://www.nurkiewicz.com/2013/05/java-8-definitive-guide-to.html
 * http://stackoverflow.com/questions/33257459/mongodb-async-java-driver-find
 */
public class MongoRepo implements IMongoRepo {
    private Configuration configuration;
    private String username;
    private String password;
    private String hostName;
    private String databaseName;
    private String connectionString;
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private Promise<List<Document>> promise;
    private Env env;

    private final String MONGO_USERNAME_TEST = "mongo_username_test";
    private final String MONGO_HOST_TEST = "mongo_host_test";
    private final String MONGO_DATABASE_TEST = "mongo_database_test";

    private final String MONGO_USERNAME_DEV = "mongo_username_dev";
    private final String MONGO_HOST_DEV = "mongo_host_dev";
    private final String MONGO_DATABASE_DEV = "mongo_database_dev";

    private final String MONGO_USERNAME = "mongo_username";
    private final String MONGO_HOST = "mongo_host";
    private final String MONGO_DATABASE = "mongo_database";

    @Inject
    public MongoRepo(Configuration configuration) {
        this.env = Env.TEST;
        this.configuration = configuration;
        switch(this.env) {
            case DEV:
                this.username = this.configuration.getString(MONGO_USERNAME_TEST);
                this.hostName = this.configuration.getString(MONGO_HOST_TEST);
                this.databaseName = this.configuration.getString(MONGO_DATABASE_TEST);
                break;
            case TEST:
                this.username = this.configuration.getString(MONGO_USERNAME_DEV);
                this.hostName = this.configuration.getString(MONGO_HOST_DEV);
                this.databaseName = this.configuration.getString(MONGO_DATABASE_DEV);
                break;
            case PROD:
            default:
                this.username = this.configuration.getString(MONGO_USERNAME);
                this.hostName = this.configuration.getString(MONGO_HOST);
                this.databaseName = this.configuration.getString(MONGO_DATABASE);
                break;
        }
        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase(this.databaseName);
    }

    public void setCollection(String collectionName) {
        this.collection = this.database.getCollection(collectionName);
    }

    public CompletionStage<List<Document>> find() {
        final List<Document> data = new ArrayList<>();
        CompletableFuture<List<Document>> future = new CompletableFuture<>();
        collection.find().into(data, (result, t) -> future.complete(result));
        return future;
    }

    public CompletionStage<List<Document>> findEqual(String field, String value) {
        final List<Document> data = new ArrayList<>();
        CompletableFuture<List<Document>> future = new CompletableFuture<>();
        // collection.find(eq(field, value)).into(data, (result, t) -> future.complete(result));
        return future;
    }
}
