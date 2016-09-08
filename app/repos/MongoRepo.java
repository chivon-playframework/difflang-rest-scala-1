package repos;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import enums.Env;
import org.apache.commons.math3.exception.NullArgumentException;
import org.bson.Document;
import play.Configuration;
import scala.concurrent.Promise;
import java.util.concurrent.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.ConnectionString;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import play.libs.F;
import play.Logger;
import java.util.Arrays;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.exclude;
import static com.mongodb.client.model.Projections.excludeId;

/**
 * The MongoDB repository
 * https://www.playframework.com/documentation/2.5.0/api/java/play/Configuration.html
 * https://groups.google.com/forum/#!topic/play-framework/degqSGUdWK8
 * https://github.com/jonasanso/play-reactive-mongo-db
 * http://www.nurkiewicz.com/2013/05/java-8-definitive-guide-to.html
 * http://stackoverflow.com/questions/33257459/mongodb-async-java-driver-find
 * http://www.slideshare.net/hermannhueck/reactive-access-to-mongodb-from-java-8
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
    private final String MONGO_CONN_STR = "mongo_conn_string";

    SingleResultCallback<Void> callbackWhenFinished = new SingleResultCallback<Void>() {
        @Override
        public void onResult(Void result, Throwable t) {
            System.out.println("Operation finished!");
        }
    };

    @Inject
    public MongoRepo(Configuration configuration) {
        this.configuration = configuration;
        this.connectionString = this.configuration.getString(MONGO_CONN_STR);
    }

    public void init(Env env) {
        switch (this.env) {
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
        if (this.connectionString == null || this.connectionString.isEmpty())
            throw new NullArgumentException();
        this.mongoClient = MongoClients.create(connectionString);
        if (this.databaseName == null || this.databaseName.isEmpty())
            throw new NullArgumentException();
        this.database = mongoClient.getDatabase(this.databaseName);
    }

    public void createCollection(String collectionName) {
        if (collectionName == null || collectionName.isEmpty())
            throw new NullArgumentException();
        if (collection == null)
            database.getCollection(collectionName);
        if (collection == null) {
            database.createCollection(collectionName,
                    new CreateCollectionOptions().capped(false),
                    callbackWhenFinished);
            this.collection = database.getCollection(collectionName);
        }
    }

    public void setCollection(String collectionName) {
        this.collection = this.database.getCollection(collectionName);
    }

    public CompletableFuture<List<Document>> find() {
        final List<Document> data = new ArrayList<>();
        CompletableFuture<List<Document>> future = new CompletableFuture<>();
        collection.find().into(data, (result, t) -> future.complete(result));
        return future;
    }

    public CompletableFuture<List<Document>> findEqual(String field, String value) {
        final List<Document> data = new ArrayList<>();
        CompletableFuture<List<Document>> future = new CompletableFuture<>();
        collection.find(eq(field, value)).into(data, (result, t) -> future.complete(result));
        return future;
    }
}
